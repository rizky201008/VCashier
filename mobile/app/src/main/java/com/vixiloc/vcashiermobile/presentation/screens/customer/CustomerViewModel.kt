package com.vixiloc.vcashiermobile.presentation.screens.customer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.domain.use_case.GetCustomers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CustomerViewModel(private val getCustomers: GetCustomers) : ViewModel() {

    var state by mutableStateOf(CustomerState())

    fun onEvent(event: CustomerEvent) {
        when (event) {
            is CustomerEvent.DismissAlertMessage -> {
                state = state.copy(error = "", success = "")
            }

            is CustomerEvent.InputCustomerName -> {

            }

            is CustomerEvent.SubmitCreateCustomer -> {

            }

            is CustomerEvent.SubmitUpdateCustomer -> {

            }

            is CustomerEvent.PreFillFormData -> {

            }

            is CustomerEvent.DeleteCustomer -> {

            }

            is CustomerEvent.ProcessDeleteCustomer -> {

            }

            is CustomerEvent.InputSearchValue -> {

            }
        }
    }

    fun getAllCustomers() {
        getCustomers().onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(isLoading = false, customers = res.data ?: emptyList())
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = res.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}