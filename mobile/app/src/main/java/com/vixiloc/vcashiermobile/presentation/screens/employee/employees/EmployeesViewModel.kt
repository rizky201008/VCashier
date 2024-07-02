package com.vixiloc.vcashiermobile.presentation.screens.employee.employees

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.model.ValidationRule
import com.vixiloc.vcashiermobile.domain.model.auth.RegisterRequest
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class EmployeesViewModel(useCaseManager: UseCaseManager) : ViewModel() {

    private val _state = mutableStateOf(EmployeesState())
    val state: State<EmployeesState> = _state

    val getUsersUseCase = useCaseManager.getUsersUseCase()
    val registerUseCase = useCaseManager.registerUseCase()
    val validateInputUseCase = useCaseManager.validateInput()

    fun onEvent(e: EmployeesEvent) {
        when (e) {
            is EmployeesEvent.ShowSuccessAlert -> {
                _state.value = state.value.copy(
                    showSuccessAlert = e.show
                )
                if (!e.show) {
                    getUsers()
                }
            }

            is EmployeesEvent.ShowErrorAlert -> {
                _state.value = state.value.copy(
                    showErrorAlert = e.show
                )
            }

            is EmployeesEvent.ShowAddDialog -> {
                _state.value = state.value.copy(
                    showAddDialog = e.show
                )
            }

            is EmployeesEvent.ChangeInput -> {
                when (e.name) {
                    InputName.NAME -> {
                        _state.value = state.value.copy(
                            name = e.value
                        )
                    }

                    InputName.EMAIL -> {
                        _state.value = state.value.copy(
                            email = e.value
                        )
                    }

                    InputName.PASSWORD -> {
                        _state.value = state.value.copy(
                            password = e.value
                        )
                    }

                    InputName.ROLE -> {
                        _state.value = state.value.copy(
                            role = e.value
                        )
                    }
                }
            }

            is EmployeesEvent.AddEmployee -> {
                createUser()
            }
        }
    }

    private fun createUser() {
        viewModelScope.launch {
            val data = RegisterRequest(
                name = state.value.name,
                email = state.value.email,
                password = state.value.password,
                role = state.value.role
            )
            registerUseCase(data).onEach { res ->
                when (res) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            success = res.data?.message ?: "",
                            isLoading = false,
                            showSuccessAlert = true,
                            email = "",
                            name = "",
                            password = ""
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            error = res.message ?: "An unexpected error occurred",
                            showErrorAlert = true,
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            getUsersUseCase().onEach { res ->
                when (res) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _state.value =
                            state.value.copy(
                                users = res.data?.data ?: emptyList(),
                                isLoading = false
                            )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            error = res.message ?: "An unexpected error occurred",
                            isLoading = false
                        )
                        onEvent(EmployeesEvent.ShowErrorAlert(true))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    init {
        getUsers()
    }

}