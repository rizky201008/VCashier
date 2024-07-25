package com.vixiloc.vcashiermobile.presentation.screens.welcome

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.use_case.GetToken
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.utils.Strings.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class WelcomeViewModel(useCaseManager: UseCaseManager) : ViewModel() {

    private val _state = mutableStateOf(WelcomeState())
    var state: State<WelcomeState> = _state
    private val getTokenUseCase: GetToken = useCaseManager.getTokenUseCase()
    private val fetchRoleUseCase = useCaseManager.fetchRoleUseCase()

    init {
        getToken()
    }

    fun showError(show: Boolean) {
        _state.value = state.value.copy(showError = show)
    }

    private fun getToken() {
        viewModelScope.launch {
            delay(3000)
            getTokenUseCase().collect { apiKey ->
                fetchRole(apiKey)
            }
        }
    }

    private fun fetchRole(apiKey: String) {
        if (apiKey.isBlank()) {
            _state.value = state.value.copy(screenReady = true, token = apiKey)
            return
        }
        fetchRoleUseCase().onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value =
                        state.value.copy(token = apiKey, screenReady = true, isLoading = false)
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        error = res.message ?: "An unexpected error occurred",
                        showError = true,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}