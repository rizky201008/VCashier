package com.vixiloc.vcashiermobile.presentation.screens.welcome

data class WelcomeState(
    val token: String = "",
    val screenReady: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
    val showError: Boolean = false
)