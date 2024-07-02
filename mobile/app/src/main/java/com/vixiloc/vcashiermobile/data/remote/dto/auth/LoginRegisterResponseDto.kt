package com.vixiloc.vcashiermobile.data.remote.dto.auth

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.auth.LoginRegisterResponse

data class LoginRegisterResponseDto(
    @SerializedName("token") val token: String? = null,
    @SerializedName("message") val message: String
)

fun LoginRegisterResponseDto.toDomain(): LoginRegisterResponse {
    return LoginRegisterResponse(
        token = token,
        message = message
    )
}
