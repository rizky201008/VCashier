package com.vixiloc.vcashiermobile.domain.model.auth

data class RegisterResponse(
    val token: String? = null,
    val message: String,
    val role: String
)
