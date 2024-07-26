package com.vixiloc.vcashiermobile.presentation.screens.transaction.process_payment

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.domain.model.payments.MakePaymentRequest
import com.vixiloc.vcashiermobile.domain.use_case.GetPaymentMethods
import com.vixiloc.vcashiermobile.domain.use_case.GetTransaction
import com.vixiloc.vcashiermobile.domain.use_case.MakePayment
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProcessPaymentViewModel(useCaseManager: UseCaseManager) : ViewModel() {
    private val _state = mutableStateOf(ProcessPaymentState())
    val state: State<ProcessPaymentState> = _state

    fun onEvent(event: ProcessPaymentEvent) {
        when (event) {

            is ProcessPaymentEvent.DismissAlertMessage -> {
                _state.value = _state.value.copy(error = "", success = "")
            }

            is ProcessPaymentEvent.InsertTransactionTotal -> {
                _state.value = _state.value.copy(transactionTotal = event.total)
            }

            is ProcessPaymentEvent.UpdatePaymentAmount -> {
                if (event.amount.isDigitsOnly() && event.amount.isNotBlank()) {
                    _state.value = _state.value.copy(paymentAmount = event.amount.toInt())
                }
            }

            is ProcessPaymentEvent.SelectPaymentMethod -> {
                _state.value = _state.value.copy(paymentMethod = event.method)
            }

            is ProcessPaymentEvent.SubmitTransactionPayment -> {
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
        viewModelScope.launch {
            makePaymentUseCase(data = data).onEach { res ->
                when (res) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            success = res.data?.message ?: "Pembayaran berhasil dibuat",
                            response = res.data,
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
            }.launchIn(viewModelScope)
        }
    }

    fun getTransaction(id: String) {
        viewModelScope.launch {
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
    }

    private fun getPaymentMethods() {
        viewModelScope.launch {
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

    init {
        getPaymentMethods()
    }
}