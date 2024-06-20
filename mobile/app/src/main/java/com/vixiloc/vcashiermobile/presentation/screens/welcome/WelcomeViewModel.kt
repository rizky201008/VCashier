package com.vixiloc.vcashiermobile.presentation.screens.welcome

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.use_case.GetToken
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeViewModel(useCaseManager: UseCaseManager) : ViewModel() {

    val _state = mutableStateOf(WelcomeState())
    var state: State<WelcomeState> = _state
    private val getToken: GetToken = useCaseManager.getTokenUseCase()

    init {
        getTokenKey()
    }

    private fun getTokenKey() {
        viewModelScope.launch {
            delay(3000)
            getToken().collect { apiKey ->
                _state.value = _state.value.copy(token = apiKey, screenReady = true)
            }
        }
    }
}