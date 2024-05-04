package com.vixiloc.vcashiermobile.presentation.screens.customer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.domain.model.CreateUpdateCustomerRequest
import com.vixiloc.vcashiermobile.domain.use_case.CreateCustomer
import com.vixiloc.vcashiermobile.domain.use_case.GetCustomers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CustomerViewModel(
    private val getCustomers: GetCustomers,
    private val createCustomer: CreateCustomer
) : ViewModel() {

    var state by mutableStateOf(CustomerState())

    fun onEvent(event: CustomerEvent) {
        when (event) {
            is CustomerEvent.DismissAlertMessage -> {
                state = state.copy(error = "", success = "")
            }

            is CustomerEvent.InputCustomerName -> {
                state = state.copy(customerName = event.name)
            }

            is CustomerEvent.InputCustomerNumber -> {
                state = state.copy(customerNumber = event.number)
            }

            is CustomerEvent.SubmitCreateCustomer -> {
                proceessCreateCustomer()
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

    private fun proceessCreateCustomer() {
        val data = CreateUpdateCustomerRequest(
            name = state.customerName,
            phoneNumber = state.customerNumber
        )
        createCustomer(data = data).onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(isLoading = false, success = "Customer created successfully")
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

    fun getAllCustomers() {
        getCustomers().onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true, customers = emptyList())
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