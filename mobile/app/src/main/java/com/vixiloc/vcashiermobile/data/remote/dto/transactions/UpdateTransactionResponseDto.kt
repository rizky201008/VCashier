package com.vixiloc.vcashiermobile.data.remote.dto.transactions

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.transactions.UpdateTransactionResponse

data class UpdateTransactionResponseDto(
    @SerializedName("message")
    val message: String,
)

fun UpdateTransactionResponseDto.toDomain() =
    UpdateTransactionResponse(
        message = message,
    )
