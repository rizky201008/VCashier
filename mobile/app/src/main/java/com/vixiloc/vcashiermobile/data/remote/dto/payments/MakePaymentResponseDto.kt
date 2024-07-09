package com.vixiloc.vcashiermobile.data.remote.dto.payments


import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage
import com.vixiloc.vcashiermobile.domain.model.payments.MakePaymentResponse

data class MakePaymentResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("id")
    val id: String
)

fun MakePaymentResponseDto.toDomain() = MakePaymentResponse(
    message = message,
    id = id
)