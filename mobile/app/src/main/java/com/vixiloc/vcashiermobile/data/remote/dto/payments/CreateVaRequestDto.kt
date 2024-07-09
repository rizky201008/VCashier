package com.vixiloc.vcashiermobile.data.remote.dto.payments

import com.google.gson.annotations.SerializedName

data class CreateVaRequestDto(
    @SerializedName("transaction_id")
    val transactionId: String,
)
