package com.vixiloc.vcashiermobile.domain.model.auth

import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRequestDto

data class LoginRequest(
    val email: String,
    val password: String
)

fun LoginRequest.toLoginRequestDto(): LoginRequestDto {
    return LoginRequestDto(
        email = this.email,
        password = this.password
    )
}
