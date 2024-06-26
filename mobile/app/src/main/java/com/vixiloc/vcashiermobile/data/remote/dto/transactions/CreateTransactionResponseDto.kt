package com.vixiloc.vcashiermobile.data.remote.dto.transactions


import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.transactions.CreateTransactionResponse

data class CreateTransactionResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("transaction_id")
    val transactionId: String
)

fun CreateTransactionResponseDto.toDomain() = CreateTransactionResponse(
    message = message,
    transactionId = transactionId
)