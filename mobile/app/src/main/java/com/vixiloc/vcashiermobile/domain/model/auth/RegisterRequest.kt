package com.vixiloc.vcashiermobile.domain.model.auth

import com.vixiloc.vcashiermobile.data.remote.dto.auth.RegisterRequestDto

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

fun RegisterRequest.toDto(): RegisterRequestDto {
    return RegisterRequestDto(
        name = name,
        email = email,
        password = password
    )
}
