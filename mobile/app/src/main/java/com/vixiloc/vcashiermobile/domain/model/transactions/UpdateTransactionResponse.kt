package com.vixiloc.vcashiermobile.domain.model.transactions


data class UpdateTransactionResponse(
    val message: String,
)

fun UpdateTransactionResponse.toDomain(): UpdateTransactionResponse {
    return UpdateTransactionResponse(
        message = message
    )
}