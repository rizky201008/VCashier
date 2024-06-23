package com.vixiloc.vcashiermobile.presentation.screens.transaction.transaction_payment

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.domain.model.payments.MakePaymentRequest
import com.vixiloc.vcashiermobile.domain.use_case.GetPaymentMethods
import com.vixiloc.vcashiermobile.domain.use_case.GetTransaction
import com.vixiloc.vcashiermobile.domain.use_case.MakePayment
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TransactionPaymentViewModel(useCaseManager: UseCaseManager) : ViewModel() {
    private val _state = mutableStateOf(TransactionPaymentState())
    val state: State<TransactionPaymentState> = _state

    fun onEvent(event: TransactionPaymentEvent) {
        when (event) {

            is TransactionPaymentEvent.DismissAlertMessage -> {
                _state.value = _state.value.copy(error = "", success = "")
            }

            is TransactionPaymentEvent.InsertTransactionTotal -> {
                _state.value = _state.value.copy(totalPrice = event.total)
            }


            is TransactionPaymentEvent.UpdatePaymentAmount -> {
                if (event.amount.isDigitsOnly() && event.amount.isNotBlank()) {
                    _state.value = _state.value.copy(paymentAmount = event.amount.toInt())
                }
            }

            is TransactionPaymentEvent.SelectPaymentMethod -> {
                _state.value = _state.value.copy(paymentMethod = event.method)
            }

            is TransactionPaymentEvent.SubmitTransactionPayment -> {
                makePayment()
            }
        }
    }

    private val getTransactionUseCase: GetTransaction = useCaseManager.getTransactionUseCase()
    private val getPaymentMethodsUseCase: GetPaymentMethods =
        useCaseManager.getPaymentMethodsUseCase()
    private val makePaymentUseCase: MakePayment = useCaseManager.makePaymentUseCase()

    private fun makePayment() {
        val data = MakePaymentRequest(
            transactionId = state.value.selectedTransactionId,
            paymentAmount = state.value.paymentAmount,
            paymentMethodId = state.value.paymentMethod?.id ?: 1
        )
        makePaymentUseCase(data = data).onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        success = res.data?.message ?: "Pembayaran berhasil dibuat",
                        isLoading = false
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

    fun getTransaction(id: String) {
        getTransactionUseCase(id).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        transactionTotal = resource.data?.totalAmount ?: 0,
                        paymentAmount = resource.data?.totalAmount ?: 0,
                        isLoading = false,
                        selectedTransactionId = id
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

    fun getPaymentMethods() {
        getPaymentMethodsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        paymentMethods = resource.data?.data ?: emptyList(),
                        paymentMethod = resource.data?.data?.firstOrNull(),
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
}