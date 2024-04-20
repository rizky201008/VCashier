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
import com.vixiloc.vcashiermobile.domain.use_case.SaveToken
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel(
    private val login: Login,
    private val saveToken: SaveToken
) : ViewModel() {

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

    private fun saveTokenKey(token: String) {
        viewModelScope.launch {
            saveToken(token)
        }
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
            Log.d(TAG, "processLogin: $resource")
            when (resource) {
                is Resource.Loading -> {
                    toggleLoading()
                }

                is Resource.Success -> {
                    state = state.copy(loginSuccess = true)
                    toggleLoading()
                    saveTokenKey(resource.data?.token ?: "")
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