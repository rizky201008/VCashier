package com.vixiloc.vcashiermobile.presentation.screens.customer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.domain.model.customers.CreateUpdateCustomerRequest
import com.vixiloc.vcashiermobile.domain.use_case.CreateCustomer
import com.vixiloc.vcashiermobile.domain.use_case.DeleteCustomer
import com.vixiloc.vcashiermobile.domain.use_case.GetCustomers
import com.vixiloc.vcashiermobile.domain.use_case.UpdateCustomer
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CustomerViewModel(
    useCaseManager: UseCaseManager,
    private var searchJob: Job? = null
) : ViewModel() {

    var state by mutableStateOf(CustomerState())
    private val getCustomers: GetCustomers = useCaseManager.getCustomersUseCase()
    private val createCustomer: CreateCustomer = useCaseManager.createCustomerUseCase()
    private val updateCustomer: UpdateCustomer = useCaseManager.updateCustomerUseCase()
    private val deleteCustomer: DeleteCustomer = useCaseManager.deleteCustomerUseCase()

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
                searchJob?.cancel()
                state = state.copy(
                    searchQuery = event.query
                )
                searchJob = viewModelScope.launch {
                    delay(1000)
                    searchCaCustomers()
                }
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

    private fun searchCaCustomers() {
        val query = state.searchQuery
        if (query.isBlank()) {
            getAllCustomers()
        } else {
            state = state.copy(
                customers = state.customers.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            )
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