package com.vixiloc.vcashiermobile.domain.model

import com.vixiloc.vcashiermobile.data.remote.dto.LoginRequestDto

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
