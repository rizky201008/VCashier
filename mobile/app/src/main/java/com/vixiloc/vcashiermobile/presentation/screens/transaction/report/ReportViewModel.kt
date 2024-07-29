package com.vixiloc.vcashiermobile.presentation.screens.transaction.report

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ReportViewModel(ucm: UseCaseManager) : ViewModel() {
    val getReportUseCase = ucm.getReportUseCase()

    private val _state = mutableStateOf(ReportState())
    val state: State<ReportState> = _state

    fun onEvent(event: ReportEvent) {
        when (event) {
            is ReportEvent.ShowError -> {
                _state.value = state.value.copy(showError = event.show)
            }
        }
    }

    private fun getReport() {
        getReportUseCase().onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _state.value = state.value.copy(isLoading = false, data = res.data)
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = res.message ?: "",
                        showError = true
                    )
                }

                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    init {
        getReport()
    }

}