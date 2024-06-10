package com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.domain.use_case.GetProducts
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CreateTransactionViewModel(useCaseManager: UseCaseManager): ViewModel() {

        var state by mutableStateOf(CreateTransactionState())
        private val getProductsUseCase: GetProducts = useCaseManager.getProductsUseCase()


        fun onEvent(event: CreateTransactionEvent) {
            when (event) {

                is CreateTransactionEvent.Refresh -> {
                    getProducts()
                }

                is CreateTransactionEvent.ClearCart -> {
                    resetCart()
                }

                is CreateTransactionEvent.SelectProduct -> {
                    if (!state.selectedProduct.any { it.containsKey(event.product) }) {
                        state = state.copy(
                            selectedProduct = state.selectedProduct.plus(mapOf(event.product to 1)),
                            totalPrice = state.totalPrice + event.product.price
                        )
                    }
                }

                is CreateTransactionEvent.DismissAlertMessage -> {
                    state = state.copy(error = "", success = "")
                }

            }
        }
        private fun resetCart() {
            state = state.copy(selectedProduct = emptyList(), totalPrice = 0)
        }

        private fun getProducts() {
            getProductsUseCase().onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            products = resource.data ?: emptyList()
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = resource.message ?: "An unexpected error occurred"
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }

}