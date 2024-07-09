package com.vixiloc.vcashiermobile.data.remote.dto.payments

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.payments.CreateVaResponse

data class CreateVaResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("va")
    val va: String
)

fun CreateVaResponseDto.toDomain() = CreateVaResponse(
    message = message,
    va = va
)