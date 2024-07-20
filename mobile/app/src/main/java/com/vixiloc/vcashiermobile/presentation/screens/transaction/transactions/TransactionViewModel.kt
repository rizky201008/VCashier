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

            is TransactionEvent.DismissAlertMessage -> {
                _state.value = state.value.copy(error = "", success = "")
            }

            is TransactionEvent.OnSearchChanged -> {
                searchJob?.cancel()
                _state.value = state.value.copy(
                    searchQuery = event.query,
                )
                searchTransactions()
            }

            is TransactionEvent.ShowTransactionAction -> {
                _state.value = state.value.copy(
                    selectedTransaction = event.data,
                    showTransactionAction = event.show
                )
            }

            is TransactionEvent.SelectStatus -> {
                filterTransactionsStatus(event.status)
            }
        }
    }

    private fun searchTransactions() {
        if (state.value.searchQuery.isBlank()) {
            return
        }
        searchJob = viewModelScope.launch {
            delay(500)
            getTransactions()
            _state.value = state.value.copy(
                transactions = state.value.transactions.filter {
                    it.customer.name.contains(state.value.searchQuery, ignoreCase = true)
                }
            )
        }
    }

    private fun filterTransactionsStatus(status: String) {
        _state.value = state.value.copy(
            status = status,
        )
        viewModelScope.launch {
            getTransactions()
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

    private suspend fun getTransactions() {
        getTransactionsUseCase(status = state.value.status).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true, transactions = emptyList())
                }

                is Resource.Success -> {
                    _state.value = state.value.copy(
                        transactions = resource.data?.data ?: emptyList(),
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = resource.message ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            getTransactions()
        }
    }

}