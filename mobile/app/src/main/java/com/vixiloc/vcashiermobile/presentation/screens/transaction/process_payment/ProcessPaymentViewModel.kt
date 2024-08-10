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

    val validateNotBlankUseCase = useCaseManager.validateNotBlankUseCase()
    val validateValueGreaterUseCase = useCaseManager.validateValueGreaterUseCase()

    fun onEvent(event: ProcessPaymentEvent) {
        when (event) {

            is ProcessPaymentEvent.DismissAlertMessage -> {
                _state.value = _state.value.copy(error = "", success = "")
            }

            is ProcessPaymentEvent.InsertTransactionTotal -> {
                _state.value = _state.value.copy(transactionTotal = event.total)
            }

            is ProcessPaymentEvent.UpdatePaymentAmount -> {
                _state.value = _state.value.copy(paymentAmount = event.amount)
                updateChange()
            }

            is ProcessPaymentEvent.SelectPaymentMethod -> {
                _state.value = _state.value.copy(paymentMethod = event.method)
            }

            is ProcessPaymentEvent.SubmitTransactionPayment -> {
                validateInputs()
            }
        }
    }

    private fun validateInputs() {
        val validatedPaymentAmount1 = validateNotBlankUseCase(state.value.paymentAmount)
        val validatedPaymentAmount2 =validateValueGreaterUseCase(state.value.paymentAmount, state.value.transactionTotal)

        val hasError = listOf(
            validatedPaymentAmount1,
            validatedPaymentAmount2
        ).any { !it.successful }

        if (hasError) {
            _state.value = _state.value.copy(
                paymentAmountError = validatedPaymentAmount1.errorMessage
                    ?: validatedPaymentAmount2.errorMessage ?: "",
            )
            return
        }
        makePayment()
    }

    private fun updateChange() {
        _state.value = state.value.copy(
            change = if (state.value.paymentAmount
                    .isNotBlank() && state.value.paymentAmount.toInt() > state.value.transactionTotal
            ) (state.value.paymentAmount.toInt() - state.value.transactionTotal) else 0
        )
    }

    private val getTransactionUseCase: GetTransaction = useCaseManager.getTransactionUseCase()
    private val getPaymentMethodsUseCase: GetPaymentMethods =
        useCaseManager.getPaymentMethodsUseCase()
    private val makePaymentUseCase: MakePayment = useCaseManager.makePaymentUseCase()

    private fun makePayment() {
        val data = MakePaymentRequest(
            transactionId = state.value.selectedTransactionId,
            paymentAmount = state.value.paymentAmount.toInt(),
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
                            totalAmount = resource.data?.totalAmount ?: 0,
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