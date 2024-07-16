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
) : ViewModel() {
    private var searchJob: Job? = null
    private val _state = mutableStateOf(CustomerState())
    val state: State<CustomerState> = _state

    private val getCustomers: GetCustomers = useCaseManager.getCustomersUseCase()
    private val createCustomer: CreateCustomer = useCaseManager.createCustomerUseCase()
    private val updateCustomer: UpdateCustomer = useCaseManager.updateCustomerUseCase()
    private val validateNotBlankUseCase = useCaseManager.validateNotBlankUseCase()

    fun onEvent(event: CustomerEvent) {
        when (event) {
            is CustomerEvent.ShowError -> {
                _state.value = _state.value.copy(showError = event.show)
            }

            is CustomerEvent.ShowSuccess -> {
                _state.value = _state.value.copy(showSuccess = event.show)
                if (!event.show) {
                    viewModelScope.launch {
                        getAllCustomers()
                    }
                }
            }

            is CustomerEvent.ShowUpdateModal -> {
                _state.value = _state.value.copy(showUpdateModal = event.show)
                if (!event.show) {
                    clearInputs()
                    clearInputErrors()
                }
            }

            is CustomerEvent.ShowCreateModal -> {
                _state.value = _state.value.copy(showCreateModal = event.show)
                if (!event.show) {
                    clearInputs()
                    clearInputErrors()
                }
            }

            is CustomerEvent.ChangeInput -> {
                when (event.type) {
                    CustomerFormInput.NAME -> {
                        _state.value = _state.value.copy(customerName = event.value)
                    }

                    CustomerFormInput.NUMBER -> {
                        if (event.value.isDigitsOnly()) {
                            _state.value = _state.value.copy(customerNumber = event.value)
                        }
                    }
                }
            }

            is CustomerEvent.CreateCustomer -> {
                validateInput(false)
            }

            is CustomerEvent.UpdateCustomer -> {
                validateInput(true)
            }

            is CustomerEvent.FillFormData -> {
                _state.value = _state.value.copy(
                    customerName = event.data?.name ?: "",
                    customerNumber = event.data?.phoneNumber ?: "",
                    customerId = event.data?.id
                )
            }

            is CustomerEvent.InputSearchValue -> {
                searchJob?.cancel()
                _state.value = _state.value.copy(
                    searchQuery = event.query
                )
                searchJob = viewModelScope.launch {
                    delay(300)
                    searchCustomers()
                }
            }
        }
    }

    private fun validateInput(update: Boolean) {
        val validatedName = validateNotBlankUseCase(state.value.customerName)

        val hasError = listOf(
            validatedName
        ).any { !it.successful }

        if (hasError) {
            _state.value = _state.value.copy(
                customerNameError = validatedName.errorMessage ?: ""
            )
            return
        }

        clearInputErrors()

        if (update) {
            processUpdateCustomer()
        } else {
            proceessCreateCustomer()
        }
    }

    private fun clearInputErrors() {
        _state.value = _state.value.copy(
            customerNameError = ""
        )
    }

    private fun clearInputs() {
        _state.value = _state.value.copy(
            customerName = "",
            customerNumber = ""
        )
    }

    private fun processUpdateCustomer() {
        val stateValue = state.value
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
                        success = "Customer updated successfully",
                        showSuccess = true,
                        customerId = null,
                        customerName = "",
                        customerNumber = "",
                        showUpdateModal = false
                    )
                    clearInputs()
                    clearInputErrors()
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = res.message ?: "An unexpected error occurred",
                        showError = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun searchCustomers() {
        val stateValue = state.value
        val query = stateValue.searchQuery
        viewModelScope.launch {
            if (query.isBlank()) {
                getAllCustomers()
                return@launch
            } else {
                getAllCustomers()
                _state.value = _state.value.copy(
                    customers = stateValue.customers.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                )
            }
        }
    }

    private fun proceessCreateCustomer() {
        val stateValue = state.value
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
                        success = "Customer created successfully",
                        showSuccess = true,
                        customerName = "",
                        customerNumber = "",
                        showCreateModal = false
                    )
                    clearInputs()
                    clearInputErrors()
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        showError = true,
                        error = res.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun getAllCustomers() {
        getCustomers().collect { res ->
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
                        showError = true,
                        error = res.message ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            getAllCustomers()
        }
    }
}