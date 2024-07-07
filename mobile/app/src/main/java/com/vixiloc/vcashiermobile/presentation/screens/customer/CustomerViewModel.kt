package com.vixiloc.vcashiermobile.presentation.screens.customer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.domain.model.customers.CreateUpdateCustomerRequest
import com.vixiloc.vcashiermobile.domain.use_case.CreateCustomer
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

    private val _state = mutableStateOf(CustomerState())
    private val state: State<CustomerState> = _state
    val stateValue = state.value

    private val getCustomers: GetCustomers = useCaseManager.getCustomersUseCase()
    private val createCustomer: CreateCustomer = useCaseManager.createCustomerUseCase()
    private val updateCustomer: UpdateCustomer = useCaseManager.updateCustomerUseCase()

    fun onEvent(event: CustomerEvent) {
        when (event) {
            is CustomerEvent.ShowError -> {
                _state.value = _state.value.copy(showError = event.show)
            }

            is CustomerEvent.ShowSuccess -> {
                _state.value = _state.value.copy(showSuccess = event.show)
            }

            is CustomerEvent.InputCustomerName -> {
                _state.value = _state.value.copy(customerName = event.name)
            }

            is CustomerEvent.InputCustomerNumber -> {
                if (event.number.isDigitsOnly()) {
                    _state.value = _state.value.copy(customerNumber = event.number)
                }
            }

            is CustomerEvent.SubmitCreateCustomer -> {
                proceessCreateCustomer()
            }

            is CustomerEvent.SubmitUpdateCustomer -> {
                processUpdateCustomer()
            }

            is CustomerEvent.PreFillFormData -> {
                _state.value = _state.value.copy(
                    customerName = event.name,
                    customerNumber = event.number,
                    customerId = event.id
                )
            }

            is CustomerEvent.InputSearchValue -> {
                searchJob?.cancel()
                _state.value = _state.value.copy(
                    searchQuery = event.query
                )
                searchJob = viewModelScope.launch {
                    delay(1000)
                    searchCustomers()
                }
            }
        }
    }

    private fun processUpdateCustomer() {
        val data = CreateUpdateCustomerRequest(
            name = stateValue.customerName,
            phoneNumber = stateValue.customerNumber,
            id = stateValue.customerId
        )

        updateCustomer(data = data).onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        success = "Customer updated successfully"
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

    private fun searchCustomers() {
        val query = stateValue.searchQuery
        if (query.isBlank()) {
            getAllCustomers()
        } else {
            getCustomers().onEach { res ->
                when (res) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true, customers = emptyList())
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            customers = stateValue.customers.filter {
                                it.name.contains(query, ignoreCase = true)
                            })
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

    private fun proceessCreateCustomer() {
        val data = CreateUpdateCustomerRequest(
            name = stateValue.customerName,
            phoneNumber = stateValue.customerNumber
        )
        createCustomer(data = data).onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        success = "Customer created successfully"
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

    fun getAllCustomers() {
        getCustomers().onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true, customers = emptyList())
                }

                is Resource.Success -> {
                    _state.value =
                        _state.value.copy(isLoading = false, customers = res.data ?: emptyList())
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