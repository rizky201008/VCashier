package com.vixiloc.vcashiermobile.data.remote.dto.payments

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.payments.CheckPaymentStatusResponse

data class CheckPaymentStatusResponseDto(
    @SerializedName("status")
    val status:String,
    @SerializedName("message")
    val message:String,
)

fun CheckPaymentStatusResponseDto.toDomain(): CheckPaymentStatusResponse {
    return CheckPaymentStatusResponse(
        status = status,
        message = message,
    )
}
