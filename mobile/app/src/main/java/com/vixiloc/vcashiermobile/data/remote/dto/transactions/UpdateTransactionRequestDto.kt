package com.vixiloc.vcashiermobile.data.remote.dto.transactions

import com.google.gson.annotations.SerializedName

data class UpdateTransactionRequestDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("transaction_status")
    val transactionStatus: String
)

fun UpdateTransactionRequestDto.toDomain() = com.vixiloc.vcashiermobile.domain.model.transactions.UpdateTransactionRequest(
    id = id,
    transactionStatus = transactionStatus
)