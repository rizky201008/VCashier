package com.vixiloc.vcashiermobile.presentation.screens.products.list_products

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem
import com.vixiloc.vcashiermobile.domain.model.categories.toCategory
import com.vixiloc.vcashiermobile.domain.use_case.GetCategories
import com.vixiloc.vcashiermobile.domain.use_case.GetProducts
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.launch

class ProductsViewModel(
    useCaseManager: UseCaseManager
) : ViewModel() {
    private val _state = mutableStateOf(ProductState())
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

            is ProductEvent.Refresh -> {
                viewModelScope.launch {
                    getCategories()
                    getProducts()
                }
            }
        }
    }

    private val defaultCategory = CategoriesResponseItem(
        id = 0,
        name = "Semua Kategori"
    )

    private fun updateCategory(category: CategoriesResponseItem?) {
        _state.value = _state.value.copy(
            selectedCategory = category ?: defaultCategory
        )
        viewModelScope.launch {
            if (category == null) {
                getProducts()
            } else {
                getProducts()
                val filteredProducts = state.value.products.filter {
                    it.category == category.toCategory()
                }
                _state.value = _state.value.copy(
                    isLoading = false,
                    products = filteredProducts
                )
            }
        }
    }

    private suspend fun getCategories() {
        getCategoriesUseCase().collect { res ->
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
        }
    }

    private suspend fun getProducts() {
        getProductsUseCase().collect { res ->
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
        }
    }

    init {
        viewModelScope.launch {
            getCategories()
            getProducts()
        }
    }
}