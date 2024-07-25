package com.vixiloc.vcashiermobile.data.remote.dto.auth

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.auth.RegisterResponse

data class RegisterResponseDto(
    @SerializedName("token") val token: String,
    @SerializedName("role") val role: String,
    @SerializedName("message") val message: String
)

fun RegisterResponseDto.toDomain(): RegisterResponse {
    return RegisterResponse(
        token = token,
        message = message,
        role = role
    )
}
