package com.vixiloc.vcashiermobile.data.remote.dto.auth

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.auth.LogoutResponse

data class LogoutResponseDto(
    @SerializedName("message")
    val message: String
)

fun LogoutResponseDto.toDomain(): LogoutResponse = LogoutResponse(
    message = message
)
