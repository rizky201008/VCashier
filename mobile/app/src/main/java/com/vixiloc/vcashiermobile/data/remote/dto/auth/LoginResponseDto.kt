package com.vixiloc.vcashiermobile.data.remote.dto.auth

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.auth.LoginResponse

data class LoginResponseDto(
    @SerializedName("token") val token: String,
    @SerializedName("role") val role: String,
    @SerializedName("message") val message: String
)

fun LoginResponseDto.toDomain(): LoginResponse {
    return LoginResponse(
        token = token,
        message = message,
        role = role
    )
}
