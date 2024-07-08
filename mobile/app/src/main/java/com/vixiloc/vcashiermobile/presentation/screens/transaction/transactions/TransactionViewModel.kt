package com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.use_case.GetProducts
import com.vixiloc.vcashiermobile.domain.use_case.GetTransactions
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TransactionViewModel(
    useCaseManager: UseCaseManager
) : ViewModel() {

    val _state = mutableStateOf(TransactionState())
    val state: State<TransactionState> = _state
    private val getProductsUseCase: GetProducts = useCaseManager.getProductsUseCase()
    private val getTransactionsUseCase: GetTransactions = useCaseManager.getTransactionsUseCase()
    private var searchJob: Job? = null

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
                _state.value = _state.value.copy(error = "", success = "")
            }

            is TransactionEvent.OnSearchChanged -> {
                searchJob?.cancel()
                _state.value = _state.value.copy(
                    searchQuery = event.query,
                )
                searchJob = viewModelScope.launch {
                    delay(1500)
                    searchTransactions()
                }
            }

            is TransactionEvent.ShowTransactionAction -> {
                _state.value = _state.value.copy(
                    selectedTransaction = event.data,
                    showTransactionAction = event.show
                )
            }

            is TransactionEvent.SelectStatus -> {
                filterTransactions(event.status)
            }
        }
    }

    private fun searchTransactions() {
        val stateValue = state.value
        val query = stateValue.searchQuery
        getTransactions()
        if (query.isNotBlank()) {
            _state.value = stateValue.copy(
                transactions = stateValue.transactions.filter {
                    it.customer.name.contains(query, ignoreCase = true)
                }
            )
        }
    }

    private fun filterTransactions(status: String) {
        _state.value = _state.value.copy(
            status = status,
        )
        getTransactions()
    }

    private fun getProducts() {
        getProductsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        products = resource.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = resource.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTransactions() {
        val state = state.value
        getTransactionsUseCase(status = state.status).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true, transactions = emptyList())
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        transactions = resource.data?.data ?: emptyList(),
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
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