package com.vixiloc.vcashiermobile.presentation.screens.login

data class LoginState(
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val loginSuccess: Boolean = false,
)
