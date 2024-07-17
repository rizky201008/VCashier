package com.vixiloc.vcashiermobile.presentation.screens.product_log.list_logs

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.presentation.screens.product_log.ProductLogEvent
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductLogViewModel(useCaseManager: UseCaseManager) : ViewModel() {
    val getProductLogsUseCase = useCaseManager.getProductLogsUseCase()
    private val _state = mutableStateOf(ProductLogState())
    val state: State<ProductLogState> = _state

    fun onEvent(event: ProductLogEvent) {
        when (event) {
            is ProductLogEvent.ShowSuccessAlert -> {
                _state.value = state.value.copy(showSuccess = event.show)
            }

            is ProductLogEvent.ShowErrorAlert -> {
                _state.value = state.value.copy(showError = event.show)
            }

            is ProductLogEvent.Refresh -> {
                getProductLogs()
            }
        }
    }

    private fun getProductLogs() {
        getProductLogsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.value =
                        state.value.copy(
                            logs = resource.data ?: emptyList(),
                            isLoading = false
                        )
                }

                is Resource.Error -> {
                    _state.value =
                        state.value.copy(
                            showError = true,
                            error = resource.message ?: "",
                            isLoading = false
                        )
                }

                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true, logs = emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

//    private fun getProducts() {
//        getProductsUseCase().onEach { resource ->
//            when (resource) {
//                is Resource.Success -> {
//                    _state.value =
//                        state.value.copy(
//                            showSuccess = true,
//                            products = resource.data ?: emptyList(),
//                            isLoading = false
//                        )
//                }
//
//                is Resource.Error -> {
//                    _state.value =
//                        state.value.copy(
//                            showError = true,
//                            error = resource.message ?: "",
//                            isLoading = false
//                        )
//                }
//
//                is Resource.Loading -> {
//                    _state.value = state.value.copy(isLoading = true)
//                }
//            }
//        }.launchIn(viewModelScope)
//    }

    init {
        getProductLogs()
    }
}