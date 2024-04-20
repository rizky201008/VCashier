package com.vixiloc.vcashiermobile.presentation.screens.welcome

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.commons.Strings.TAG
import com.vixiloc.vcashiermobile.domain.use_case.GetToken
import kotlinx.coroutines.launch

class WelcomeViewModel(private val getToken: GetToken) : ViewModel() {

    var state by mutableStateOf(WelcomeState())

    init {
        getTokenKey()
    }
    private fun getTokenKey() {
        viewModelScope.launch {
            getToken().collect { apiKey ->
                state = state.copy(token = apiKey)
                Log.d(TAG, "getTokenKey: $state")
            }
        }
    }
}