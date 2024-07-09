package com.vixiloc.vcashiermobile.presentation.screens.transaction.pay_transaction

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.model.payments.CreateVaRequest
import com.vixiloc.vcashiermobile.domain.model.transactions.TransactionResponse
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PayTransactionViewModel(useCaseManager: UseCaseManager) : ViewModel() {
    private val _state = mutableStateOf(PayTransactionState())
    val state: State<PayTransactionState> = _state
    val getTransactionUseCase = useCaseManager.getTransactionUseCase()
    val createVaUseCase = useCaseManager.createVaUseCase()

    fun oneEvent(event: PayTransactionEvent) {
        when (event) {
            is PayTransactionEvent.DismissAlertMessage -> {
                _state.value = _state.value.copy(
                    error = "",
                    success = ""
                )
            }

            is PayTransactionEvent.GetTransaction -> {
                getTransaction(event.id)
            }
        }
    }

    private fun getTransaction(id: String) {
        getTransactionUseCase(id).onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    if (res.data != null) {
                        if (res.data.vaNumber == null) {
                            createVa(res.data)
                        } else {
                            _state.value = _state.value.copy(
                                transactionData = res.data
                            )
                        }
                    }
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
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

    private fun createVa(data: TransactionResponse) {
        val reqData = CreateVaRequest(
            transactionId = data.id,
        )
        createVaUseCase(reqData).onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    val newTransactionData = TransactionResponse(
                        change = data.change,
                        id = data.id,
                        vaNumber = res.data?.va,
                        paymentAmount = data.paymentAmount,
                        transactionStatus = data.transactionStatus,
                        customerId = data.customerId,
                        paymentMethodId = data.paymentMethodId,
                        items = data.items,
                        paymentStatus = data.paymentStatus,
                        totalAmount = data.totalAmount,
                        userId = data.userId,
                        paymentMethod = data.paymentMethod

                    )
                    _state.value = _state.value.copy(
                        isLoading = false,
                        transactionData = newTransactionData
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = res.message ?: "An unexpected error occurred",
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun generateVa() {

    }
}