package com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.domain.use_case.GetProducts
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CreateTransactionViewModel(useCaseManager: UseCaseManager) : ViewModel() {

    private val _state = mutableStateOf(CreateTransactionState())
    val state: State<CreateTransactionState> = _state

    private val getProductsUseCase: GetProducts = useCaseManager.getProductsUseCase()


    fun onEvent(event: CreateTransactionEvent) {
        when (event) {

            is CreateTransactionEvent.Refresh -> {
                getProducts()
            }

            is CreateTransactionEvent.ClearCart -> {
                resetCart()
            }

            is CreateTransactionEvent.AddToCart -> {
                _state.value = state.value.copy(selectedProduct = event.variation)
            }

            is CreateTransactionEvent.DismissAlertMessage -> {
                _state.value = state.value.copy(error = "", success = "")
            }

            is CreateTransactionEvent.DismissAddToCartModal -> {
                _state.value = state.value.copy(selectedProduct = null)
            }

        }
    }

    private fun resetCart() {
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

}