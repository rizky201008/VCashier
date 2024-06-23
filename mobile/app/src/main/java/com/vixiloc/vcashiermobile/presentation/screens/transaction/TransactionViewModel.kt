package com.vixiloc.vcashiermobile.presentation.screens.transaction

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.commons.Strings.TAG
import com.vixiloc.vcashiermobile.domain.model.transactions.CreateTransactionRequest
import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem
import com.vixiloc.vcashiermobile.domain.model.transactions.Item
import com.vixiloc.vcashiermobile.domain.model.transactions.TransactionsData
import com.vixiloc.vcashiermobile.domain.use_case.CreateTransaction
import com.vixiloc.vcashiermobile.domain.use_case.GetCustomers
import com.vixiloc.vcashiermobile.domain.use_case.GetPaymentMethods
import com.vixiloc.vcashiermobile.domain.use_case.GetProducts
import com.vixiloc.vcashiermobile.domain.use_case.GetTransaction
import com.vixiloc.vcashiermobile.domain.use_case.GetTransactions
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TransactionViewModel(
   useCaseManager: UseCaseManager
) : ViewModel() {

    var state by mutableStateOf(TransactionState())
    private val getProductsUseCase: GetProducts = useCaseManager.getProductsUseCase()
    private val createTransactionUseCase: CreateTransaction =
        useCaseManager.createTransactionUseCase()
    private val getCustomersUseCase: GetCustomers = useCaseManager.getCustomersUseCase()
    private val getTransactionsUseCase: GetTransactions = useCaseManager.getTransactionsUseCase()
    private val getTransactionUseCase: GetTransaction = useCaseManager.getTransactionUseCase()
    private val getPaymentMethodsUseCase: GetPaymentMethods =
        useCaseManager.getPaymentMethodsUseCase()


    fun onEvent(event: TransactionEvent) {
        when (event) {

            is TransactionEvent.Refresh -> {
                getProducts()
            }

            is TransactionEvent.ClearCart -> {
                resetCart()
            }

            is TransactionEvent.SelectProduct -> {
                if (!state.selectedProduct.any { it.containsKey(event.product) }) {
                    state = state.copy(
                        selectedProduct = state.selectedProduct.plus(mapOf(event.product to 1)),
                        totalPrice = state.totalPrice + event.product.price
                    )
                }
            }

            is TransactionEvent.IncreaseQty -> {
                val product = state.selectedProduct.first { it.containsKey(event.product) }
                val quantity = product[event.product] ?: 0
                state = state.copy(
                    selectedProduct = state.selectedProduct.map {
                        if (it.containsKey(event.product)) {
                            mapOf(event.product to quantity + 1)
                        } else {
                            it
                        }
                    },
                    totalPrice = state.totalPrice + event.product.price
                )
            }

            is TransactionEvent.DecreaseQty -> {
                val product = state.selectedProduct.first { it.containsKey(event.product) }
                val quantity = product[event.product] ?: 0
                if (quantity > 1) {
                    state = state.copy(
                        selectedProduct = state.selectedProduct.map {
                            if (it.containsKey(event.product)) {
                                mapOf(event.product to quantity - 1)
                            } else {
                                it
                            }
                        },
                        totalPrice = state.totalPrice - event.product.price
                    )
                } else {
                    state = state.copy(
                        selectedProduct = state.selectedProduct.filter { !it.containsKey(event.product) },
                        totalPrice = state.totalPrice - event.product.price
                    )
                }
            }

            is TransactionEvent.UpdateCustomer -> {
                state = state.copy(
                    customer = event.customer ?: CustomerResponseItem(
                        id = 1,
                        name = "Tamu",
                        phoneNumber = "-"
                    )
                )
            }

            is TransactionEvent.DismissAlertMessage -> {
                state = state.copy(error = "", success = "")
            }

            is TransactionEvent.SubmitTransaction -> {
                createTransaction()
            }

            is TransactionEvent.InsertSelectedProducts -> {
                state = state.copy(selectedProduct = event.products)
            }

            is TransactionEvent.SearchQueryChanged -> {
                state = state.copy(
                    searchQuery = event.query,
                    searchResult = state.transactions.filter { transactionsData: TransactionsData ->
                        transactionsData.customer?.name?.contains(event.query) ?: false
                    })
            }

            is TransactionEvent.SearchStatusChanged -> {
                state = state.copy(searchStatus = event.status)
            }

            is TransactionEvent.InsertTransactionTotal -> {
                state = state.copy(totalPrice = event.total)
            }


            is TransactionEvent.UpdatePaymentAmount -> {
                if (event.amount.isDigitsOnly() && event.amount.isNotBlank()) {
                    state = state.copy(paymentAmount = event.amount.toInt())
                }
            }

            is TransactionEvent.SelectPaymentMethod -> {
                state = state.copy(paymentMethod = event.method)
                Log.i(TAG, "onEvent: ${state.paymentMethod}")
            }

            is TransactionEvent.SelectTransaction -> {
                state = state.copy(selectedTransactionId = event.id)
            }

            is TransactionEvent.SubmitTransactionPayment -> {
                makePayment()
            }
        }
    }

    private fun makePayment() {
        TODO("Not yet implemented")
    }

    private fun resetCart() {
        state = state.copy(selectedProduct = emptyList(), totalPrice = 0)
    }

    fun getCustomers() {
        getCustomersUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(customers = resource.data ?: emptyList(), isLoading = false)
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

    private fun createTransaction() {
        val items = state.selectedProduct.flatMap { map ->
            map.map { (product, quantity) ->
                Item(id = product.id, quantity = quantity)
            }
        }
        val data = CreateTransactionRequest(
            customerId = state.customer.id,
            items = items
        )

        createTransactionUseCase(data).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(
                        success = resource.data?.message ?: "Transaction created successfully",
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

    fun getTransactions() {
        getTransactionsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(
                        transactions = resource.data?.data ?: emptyList(),
                        searchResult = resource.data?.data ?: emptyList(),
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

    fun getTransaction(id: String) {
        getTransactionUseCase(id).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(
                        transactionTotal = resource.data?.totalAmount ?: 0,
                        paymentAmount = resource.data?.totalAmount ?: 0,
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

    fun getPaymentMethods() {
        getPaymentMethodsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(
                        paymentMethods = resource.data?.data ?: emptyList(),
                        paymentMethod = resource.data?.data?.first(),
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

}