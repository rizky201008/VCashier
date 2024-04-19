package com.vixiloc.vcashiermobile.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.LoginResponse

data class LoginResponseDto(
    @SerializedName("token") val token: String? = null,
    @SerializedName("message") val message: String
)

fun LoginResponseDto.toLoginResponse(): LoginResponse {
    return LoginResponse(
        token = token,
        message = message
    )
}
