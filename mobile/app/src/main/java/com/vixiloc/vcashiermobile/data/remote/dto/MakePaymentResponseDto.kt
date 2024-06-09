package com.vixiloc.vcashiermobile.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage

data class MakePaymentResponseDto(
    @SerializedName("message")
    val message: String
)

fun MakePaymentResponseDto.toDomain() = OnlyResponseMessage(
    message = message
)