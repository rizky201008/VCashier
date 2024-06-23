package com.vixiloc.vcashiermobile.data.remote.dto.payments


import com.google.gson.annotations.SerializedName

data class MakePaymentRequestDto(
    @SerializedName("payment_amount")
    val paymentAmount: Int,
    @SerializedName("payment_method_id")
    val paymentMethodId: Int,
    @SerializedName("transaction_id")
    val transactionId: String
)