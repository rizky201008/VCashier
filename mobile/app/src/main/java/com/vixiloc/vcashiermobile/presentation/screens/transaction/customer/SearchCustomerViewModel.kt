package com.vixiloc.vcashiermobile.presentation.screens.transaction.customer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchCustomerViewModel(useCaseManager: UseCaseManager) : ViewModel() {
    val _state = mutableStateOf(SearchCustomerState())
    val state: State<SearchCustomerState> = _state

    val getCustomersUseCase = useCaseManager.getCustomersUseCase()

    fun onEvent(e: SearchCustomerEvent) {
        when (e) {
            is SearchCustomerEvent.SetSearchValue -> {
                _state.value = state.value.copy(searchValue = e.searchValue)
                searchCustomer()
            }

            is SearchCustomerEvent.SetSelectedCustomer -> {
                _state.value = state.value.copy(selectedCustomer = e.customer)
            }

            is SearchCustomerEvent.ShowErrorAlert -> {
                _state.value = state.value.copy(showError = e.show)
            }

            is SearchCustomerEvent.ShowSuccessAlert -> {
                _state.value = state.value.copy(showSuccess = e.show)
            }
        }
    }

    private fun searchCustomer() {
        if (state.value.searchValue.isBlank()) {
            getCustomers()
            return
        }
        val searchResult = state.value.customers.filter {
            it.name.contains(state.value.searchValue, ignoreCase = true)
        }
        _state.value = _state.value.copy(customers = searchResult)
    }

    fun getCustomers() {
        viewModelScope.launch {
            getCustomersUseCase().onEach { res ->
                when (res) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            customers = res.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = res.message ?: "An unexpected error occurred",
                            showError = true,
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    init {
        getCustomers()
    }
}