package com.vixiloc.vcashiermobile.presentation.screens.transaction.pay_transaction

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PayTransactionViewModel(useCaseManager: UseCaseManager) : ViewModel() {
    private val _state = mutableStateOf(PayTransactionState())
    val state: State<PayTransactionState> = _state
    val getTransactionUseCase = useCaseManager.getTransactionUseCase()

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
                        transactionData = res.data,
                        isLoading = false
                    )
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
}