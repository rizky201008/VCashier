package com.vixiloc.vcashiermobile.presentation.screens.products.list_products

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem
import com.vixiloc.vcashiermobile.domain.model.categories.toCategory
import com.vixiloc.vcashiermobile.utils.FileConverter
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.domain.use_case.GetCategories
import com.vixiloc.vcashiermobile.domain.use_case.GetProducts
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductsViewModel(
    private val fileConverter: FileConverter,
    useCaseManager: UseCaseManager
) : ViewModel() {
    val _state = mutableStateOf(ProductState())
    val state: State<ProductState> = _state

    private val getProductsUseCase: GetProducts = useCaseManager.getProductsUseCase()
    private val getCategoriesUseCase: GetCategories = useCaseManager.getCategoriesUseCase()

    fun onEvent(e: ProductEvent) {
        when (e) {
            is ProductEvent.ShowErrorAlert -> {
                _state.value = _state.value.copy(showError = e.show)
            }

            is ProductEvent.ShowSuccessAlert -> {
                _state.value = _state.value.copy(showSuccess = e.show)
            }

            is ProductEvent.ShowMessageAlert -> {
                _state.value = _state.value.copy(showMessage = e.show)
            }

            is ProductEvent.SelectCategory -> {
                updateCategory(e.category)
            }
        }
    }

    val defaultCategory = CategoriesResponseItem(
        id = 0,
        name = "Semua Kategori"
    )

    private fun updateCategory(category: CategoriesResponseItem?) {
        _state.value = _state.value.copy(
            selectedCategory = category ?: defaultCategory
        )
        if (category == null) {
            getProducts()
        } else {
            getProductsUseCase().onEach { res ->
                when (res) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val filteredProducts = res.data?.filter {
                            it.category == category.toCategory()
                        } ?: emptyList()
                        _state.value = _state.value.copy(
                            isLoading = false,
                            products = filteredProducts
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = res.message ?: "An unexpected error occurred"
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getCategories() {
        getCategoriesUseCase().onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        categories = res.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = res.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getProducts() {
        getProductsUseCase().onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        products = res.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = res.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    init {
        getCategories()
        getProducts()
    }
}