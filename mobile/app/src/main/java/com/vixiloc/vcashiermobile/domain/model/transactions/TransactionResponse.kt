package com.vixiloc.vcashiermobile.domain.model.transactions

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("change")
    val change: Int?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("customer_id")
    val customerId: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("items")
    val items: List<TransactionsItem>,
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
    val vaNumber: String?
)