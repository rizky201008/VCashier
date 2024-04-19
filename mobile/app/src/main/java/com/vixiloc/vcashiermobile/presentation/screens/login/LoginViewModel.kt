package com.vixiloc.vcashiermobile.presentation.screens.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.commons.Strings.TAG
import com.vixiloc.vcashiermobile.domain.model.LoginRequest
import com.vixiloc.vcashiermobile.domain.use_case.Login
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginViewModel(private val login: Login) : ViewModel() {

    var state by mutableStateOf(LoginState())

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> {
                state = state.copy(email = event.value)
            }

            is LoginEvent.OnPasswordChanged -> {
                state = state.copy(password = event.value)
            }

            is LoginEvent.Login -> {
                processLogin()
            }

            is LoginEvent.DismissAlertMessage -> {
                state = state.copy(errorMessage = "")
            }
        }
    }

    private fun processLogin() {
        val data = LoginRequest(
            email = state.email,
            password = state.password
        )
        login(data).onEach { resource ->
            Log.d(TAG, "processLogin: $resource")
            when (resource) {
                is Resource.Loading -> {
                    Log.d(TAG, "processLogin: Loading")
                    state = state.copy(isLoading = true)
                }
                is Resource.Success -> {
                    Log.d(TAG, "processLogin: Success ${resource.data}")
                    state = state.copy(isLoading = false, loginSuccess = true)
                }
                is Resource.Error -> {
                    Log.d(TAG, "processLogin: Error ${resource.message}")
                    state = state.copy(isLoading = false, errorMessage = resource.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}