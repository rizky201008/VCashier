package com.vixiloc.vcashiermobile.presentation.screens.customer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.domain.model.CreateUpdateCustomerRequest
import com.vixiloc.vcashiermobile.domain.use_case.CreateCustomer
import com.vixiloc.vcashiermobile.domain.use_case.DeleteCustomer
import com.vixiloc.vcashiermobile.domain.use_case.GetCustomers
import com.vixiloc.vcashiermobile.domain.use_case.UpdateCustomer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CustomerViewModel(
    private val getCustomers: GetCustomers,
    private val createCustomer: CreateCustomer,
    private val updateCustomer: UpdateCustomer,
    private val deleteCustomer: DeleteCustomer
) : ViewModel() {

    var state by mutableStateOf(CustomerState())

    fun onEvent(event: CustomerEvent) {
        when (event) {
            is CustomerEvent.DismissAlertMessage -> {
                state = state.copy(
                    error = "",
                    success = "",
                    confirmationMessage = "",
                    customerId = null
                )
            }

            is CustomerEvent.InputCustomerName -> {
                state = state.copy(customerName = event.name)
            }

            is CustomerEvent.InputCustomerNumber -> {
                if (event.number.isDigitsOnly()) {
                    state = state.copy(customerNumber = event.number)
                }
            }

            is CustomerEvent.SubmitCreateCustomer -> {
                proceessCreateCustomer()
            }

            is CustomerEvent.SubmitUpdateCustomer -> {
                processUpdateCustomer()
            }

            is CustomerEvent.PreFillFormData -> {
                state = state.copy(
                    customerName = event.name,
                    customerNumber = event.number,
                    customerId = event.id
                )
            }

            is CustomerEvent.DeleteCustomer -> {
                state = state.copy(customerId = event.data.id)
                showConfirmationDialog(name = event.data.name)
            }

            is CustomerEvent.ProcessDeleteCustomer -> {
                processDeleteCustomer()
            }

            is CustomerEvent.InputSearchValue -> {

            }
        }
    }

    private fun showConfirmationDialog(name: String) {
        state = state.copy(confirmationMessage = "Anda ingin menghapus pelanggan $name?")
    }

    private fun processDeleteCustomer() {
        val id = state.customerId.toString()
        deleteCustomer(id).onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    state = state.copy(confirmationMessage = "", isLoading = true)
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = res.message ?: "An error unexpected!",
                        customerId = null
                    )
                }

                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        success = res.data?.message ?: "Success",
                        customerId = null,
                    )
                    getAllCustomers()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun processUpdateCustomer() {
        val data = CreateUpdateCustomerRequest(
            name = state.customerName,
            phoneNumber = state.customerNumber,
            id = state.customerId
        )

        updateCustomer(data = data).onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(isLoading = false, success = "Customer updated successfully")
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