package com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem
import com.vixiloc.vcashiermobile.domain.model.categories.toCategory
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems
import com.vixiloc.vcashiermobile.domain.use_case.GetCartItems
import com.vixiloc.vcashiermobile.domain.use_case.GetProducts
import com.vixiloc.vcashiermobile.domain.use_case.InsertCart
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CreateTransactionViewModel(useCaseManager: UseCaseManager) : ViewModel() {

    private var searchJob: Job? = null
    private val _state = mutableStateOf(CreateTransactionState())
    val state: State<CreateTransactionState> = _state

    private val getProductsUseCase: GetProducts = useCaseManager.getProductsUseCase()
    private val addToCartUseCase: InsertCart = useCaseManager.insertCartUseCase()
    private val getCartItemsUseCase: GetCartItems = useCaseManager.getCartItemsUseCase()
    private val getCategoriesUseCase = useCaseManager.getCategoriesUseCase()


    fun onEvent(event: CreateTransactionEvent) {
        when (event) {

            is CreateTransactionEvent.Refresh -> {
                getProducts()
            }

            is CreateTransactionEvent.AddVariation -> {
                _state.value = state.value.copy(
                    selectedVariation = event.variation,
                    selectedProduct = event.product
                )
            }

            is CreateTransactionEvent.DismissAlertMessage -> {
                _state.value = state.value.copy(error = "", success = "")
            }

            is CreateTransactionEvent.DismissAddToCartModal -> {
                _state.value = state.value.copy(selectedVariation = null, selectedProduct = null)
            }

            is CreateTransactionEvent.AddToCart -> {
                addToCart(event.item)
            }

            is CreateTransactionEvent.SelectCategory -> {
                updateCategory(event.category)
            }

            is CreateTransactionEvent.UpdateSearchValue -> {
                searchJob?.cancel()
                _state.value = state.value.copy(searchQuery = event.value)
                searchJob = viewModelScope.launch {
                    delay(1000)
                    searchProducts()
                }
            }

        }
    }

    private fun searchProducts() {
        val query = state.value.searchQuery
        if (query.isBlank()) {
            getProducts()
        } else {
            _state.value = _state.value.copy(
                products = state.value.products.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            )
        }
    }

    private fun addToCart(item: CartItems) {
        viewModelScope.launch {
            addToCartUseCase(item)
        }
    }

    private fun getCartItems() {
        viewModelScope.launch {
            getCartItemsUseCase().onEach {
                _state.value = state.value.copy(cartItems = it)
            }.stateIn(viewModelScope)
        }
    }

    private fun updateCategory(category: CategoriesResponseItem?) {
        _state.value = state.value.copy(selectedCategory = category)
        if (category == null) {
            getProducts()
        } else {
            getProductsUseCase().onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val filteredProducts = resource.data?.filter {
                            it.category == category.toCategory()
                        }
                        _state.value = _state.value.copy(
                            products = filteredProducts ?: emptyList(),
                            isLoading = false,
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            error = resource.message ?: "An unexpected error occurred"
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getProducts() {
        getProductsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        products = resource.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = resource.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().onEach { res ->
                when (res) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            categories = res.data ?: emptyList()
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            error = res.message ?: "An unexpected error occurred"
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    init {
        getProducts()
        getCartItems()
        getCategories()
    }

}