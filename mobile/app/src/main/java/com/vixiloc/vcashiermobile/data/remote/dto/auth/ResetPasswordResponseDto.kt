package com.vixiloc.vcashiermobile.data.remote.dto.auth

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.auth.ResetPasswordResponse

data class ResetPasswordResponseDto(
    @SerializedName("message")
    val message: String
)

fun ResetPasswordResponseDto.toDomain() = ResetPasswordResponse(message = message)