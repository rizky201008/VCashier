package com.vixiloc.vcashiermobile.presentation.screens.employee

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val resetPasswordUseCase = useCaseManager.resetPasswordUseCase()
    val validateNotBlankUseCase = useCaseManager.validateNotBlankUseCase()
    val validateMatchUseCase = useCaseManager.validateMatchUseCase()

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
                if (!e.show) {
                    clearErrors()
                    clearInputs()
                }
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

                    InputName.PASSWORD_CONFIRMATION -> {
                        _state.value = state.value.copy(
                            passwordConfirmation = e.value
                        )
                    }
                }
            }

            is EmployeesEvent.AddEmployee -> {
                validateInputs()
            }

            is EmployeesEvent.ShowResetPasswordAlert -> {
                _state.value = _state.value.copy(
                    showResetPasswordAlert = e.show
                )
                if (!e.show) {
                    getUsers()
                }
            }

            is EmployeesEvent.ResetPassword -> {
                resetPassword()
            }

            is EmployeesEvent.SelectEmployee -> {
                _state.value = _state.value.copy(selectedEmployee = e.id)
            }

            is EmployeesEvent.TogglePasswordVisibility -> {
                _state.value = _state.value.copy(
                    passwordHidden = e.show
                )
            }
        }
    }

    private fun resetPassword() {
        viewModelScope.launch {
            resetPasswordUseCase(state.value.selectedEmployee.toString()).onEach { res ->
                when (res) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = res.message ?: "Error Unknowns",
                            showErrorAlert = true
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            success = res.data?.message ?: "Reset password sukses",
                            selectedEmployee = null
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun validateInputs() {
        val validatedName = validateNotBlankUseCase(state.value.name)
        val validatedEmail = validateNotBlankUseCase(state.value.email)
        val validatedPassword = validateNotBlankUseCase(state.value.password)
        val validatedPasswordConfirmation =
            validateMatchUseCase(state.value.passwordConfirmation, state.value.password)

        val hasError = listOf(
            validatedName,
            validatedEmail,
            validatedPassword,
            validatedPasswordConfirmation
        ).any { !it.successful }

        if (hasError) {
            _state.value = _state.value.copy(
                nameError = validatedName.errorMessage ?: "",
                emailError = validatedEmail.errorMessage ?: "",
                passwordError = validatedPassword.errorMessage ?: "",
                passwordConfirmationError = validatedPasswordConfirmation.errorMessage ?: ""
            )
            return
        }
        clearErrors()
        createUser()
    }

    private fun clearErrors() {
        _state.value = _state.value.copy(
            nameError = "",
            emailError = "",
            passwordError = ""
        )
    }

    private fun clearInputs() {
        _state.value = _state.value.copy(
            name = "",
            email = "",
            password = ""
        )
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
                            showAddDialog = false
                        )
                        clearErrors()
                        clearInputs()
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