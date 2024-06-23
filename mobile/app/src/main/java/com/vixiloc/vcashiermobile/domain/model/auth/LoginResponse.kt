package com.vixiloc.vcashiermobile.domain.model.auth

data class LoginResponse(
    val token: String? = null,
    val message: String
)
