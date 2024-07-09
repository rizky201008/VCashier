package com.vixiloc.vcashiermobile.domain.model.transactions

data class TransactionResponse(
    val change: Int?,
    val customerId: Int,
    val id: String,
    val items: List<TransactionsItem>,
    val paymentAmount: Int?,
    val paymentMethodId: Int?,
    val paymentStatus: String,
    val totalAmount: Int,
    val transactionStatus: String,
    val userId: Int,
    val vaNumber: String?,
    val paymentMethod: TransactionPaymentMethod?
)


data class TransactionPaymentMethod(
    val id: Int,
    val name: String,
    val cash: Boolean,
    val code: String,
    val fee: Int
)