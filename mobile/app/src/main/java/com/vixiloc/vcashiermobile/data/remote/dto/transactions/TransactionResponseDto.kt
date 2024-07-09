package com.vixiloc.vcashiermobile.data.remote.dto.transactions


import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.transactions.TransactionPaymentMethod
import com.vixiloc.vcashiermobile.domain.model.transactions.TransactionResponse

data class TransactionResponseDto(
    @SerializedName("change")
    val change: Int?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("customer_id")
    val customerId: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("items")
    val items: List<TransactionsItemDto>,
    @SerializedName("payment_amount")
    val paymentAmount: Int?,
    @SerializedName("payment_method_id")
    val paymentMethodId: Int?,
    @SerializedName("payment_status")
    val paymentStatus: String,
    @SerializedName("total_amount")
    val totalAmount: Int,
    @SerializedName("transaction_status")
    val transactionStatus: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("va_number")
    val vaNumber: String?,
    @SerializedName("payment_method")
    val paymentMethod: TransactionPaymentMethodDto?
)

data class TransactionPaymentMethodDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("cash")
    val cash: Boolean,
    @SerializedName("code")
    val code: String,
    @SerializedName("fee")
    val fee: Int,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)

fun TransactionPaymentMethodDto.toDomain() = TransactionPaymentMethod(
    id = id,
    name = name,
    cash = cash,
    code = code,
    fee = fee
)

fun TransactionResponseDto.toDomain(): TransactionResponse {
    return TransactionResponse(
        change = change,
        customerId = customerId,
        id = id,
        items = items.map { it.toDomain() },
        paymentAmount = paymentAmount,
        paymentMethodId = paymentMethodId,
        paymentStatus = paymentStatus,
        totalAmount = totalAmount,
        transactionStatus = transactionStatus,
        userId = userId,
        vaNumber = vaNumber,
        paymentMethod = paymentMethod?.toDomain()
    )
}