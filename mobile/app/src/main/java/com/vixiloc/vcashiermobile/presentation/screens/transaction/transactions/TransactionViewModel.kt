package com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.use_case.GetProducts
import com.vixiloc.vcashiermobile.domain.use_case.GetTransactions
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TransactionViewModel(
    useCaseManager: UseCaseManager
) : ViewModel() {

    var state by mutableStateOf(TransactionState())
    private val getProductsUseCase: GetProducts = useCaseManager.getProductsUseCase()
    private val getTransactionsUseCase: GetTransactions = useCaseManager.getTransactionsUseCase()


    fun onEvent(event: TransactionEvent) {
        when (event) {

            is TransactionEvent.Refresh -> {
                getProducts()
            }

            is TransactionEvent.SelectProduct -> {
//                if (!state.selectedProduct.any { it.containsKey(event.product) }) {
//                    state = state.copy(
//                        selectedProduct = state.selectedProduct.plus(mapOf(event.product to 1)),
//                        totalPrice = state.totalPrice + event.product.price
//                    )
//                }
            }

            is TransactionEvent.DismissAlertMessage -> {
                state = state.copy(error = "", success = "")
            }

            is TransactionEvent.OnSearchChanged -> {
                state = state.copy(
                    searchQuery = event.query,
                )
            }

            is TransactionEvent.SelectTransaction -> {
                state = state.copy(selectedTransactionId = event.id)
            }

            is TransactionEvent.SelectStatus -> {
                filterTransactions(event.status)
            }
        }
    }

    private fun filterTransactions(status: String) {
        state = state.copy(
            status = status,
        )
        getTransactions()
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

    private fun getTransactions() {
        getTransactionsUseCase(status = state.status).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true, transactions = emptyList())
                }

                is Resource.Success -> {
                    state = state.copy(
                        transactions = resource.data?.data ?: emptyList(),
                        isLoading = false
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

    init {
        getTransactions()
    }

}