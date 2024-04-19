package com.vixiloc.vcashiermobile.presentation.screens.login

sealed class LoginEvent {
    data class OnEmailChanged(val value: String) : LoginEvent()
    data class OnPasswordChanged(val value: String) : LoginEvent()
    data object Login : LoginEvent()
    data object DismissAlertMessage : LoginEvent()
}
