package com.vixiloc.vcashiermobile.presentation.screens.login

data class LoginState(
    val email: String = "admin@vc.vixiloc.com",
    val password: String = "password",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val loginSuccess: Boolean = false,
)
