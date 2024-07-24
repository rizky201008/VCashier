package com.vixiloc.vcashiermobile.domain.model.transactions

import com.vixiloc.vcashiermobile.data.remote.dto.transactions.UpdateTransactionRequestDto

data class UpdateTransactionRequest(
    val id: String,
    val transactionStatus: String
)

fun UpdateTransactionRequest.toDto() = UpdateTransactionRequestDto(
    id = id,
    transactionStatus = transactionStatus
)