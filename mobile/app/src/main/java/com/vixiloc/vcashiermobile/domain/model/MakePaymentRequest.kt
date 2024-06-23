package com.vixiloc.vcashiermobile.domain.model

import com.vixiloc.vcashiermobile.data.remote.dto.payments.MakePaymentRequestDto

data class MakePaymentRequest(
    val paymentAmount: Int,
    val paymentMethodId: Int,
    val transactionId: String
)

fun MakePaymentRequest.toDto() = MakePaymentRequestDto(
    paymentAmount = paymentAmount,
    paymentMethodId = paymentMethodId,
    transactionId = transactionId
)