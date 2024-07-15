package com.vixiloc.vcashiermobile.presentation.screens.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.utils.Strings.TAG
import com.vixiloc.vcashiermobile.domain.model.auth.LoginRequest
import com.vixiloc.vcashiermobile.domain.use_case.Login
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginViewModel(
    useCaseManager: UseCaseManager
) : ViewModel() {

    var state by mutableStateOf(LoginState())
    private val login: Login = useCaseManager.loginUseCase()
    private val validateNotBlankUseCase = useCaseManager.validateNotBlankUseCase()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> {
                state = state.copy(email = event.value)
            }

            is LoginEvent.OnPasswordChanged -> {
                state = state.copy(password = event.value)
            }

            is LoginEvent.Login -> {
                validateInput()
            }

            is LoginEvent.DismissAlertMessage -> {
                state = state.copy(errorMessage = "")
            }
        }
    }

    private fun validateInput() {
        val validatedEmail = validateNotBlankUseCase(state.email)
        val validatedPassword = validateNotBlankUseCase(state.email)
        val hasError = listOf(
            validatedEmail,
            validatedPassword
        ).any { !it.successful }
        if (hasError) {
            state = state.copy(
                emailError = validatedEmail.errorMessage ?: "",
                passwordError = validatedPassword.errorMessage ?: ""
            )
            return
        }
        clearInputErrors()
        processLogin()
    }

    private fun clearInputErrors() {
        state = state.copy(
            emailError = "",
            passwordError = ""
        )
    }

    private fun toggleLoading() {
        state = state.copy(isLoading = !state.isLoading)
    }

    private fun processLogin() {
        val data = LoginRequest(
            email = state.email,
            password = state.password
        )
        login(data).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    toggleLoading()
                }

                is Resource.Success -> {
                    state = state.copy(loginSuccess = true)
                    toggleLoading()
                }

                is Resource.Error -> {
                    state = state.copy(
                        errorMessage = resource.message ?: "An unexpected error occurred"
                    )
                    toggleLoading()
                }
            }
        }.launchIn(viewModelScope)
    }
}