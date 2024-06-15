package com.vixiloc.vcashiermobile.presentation.screens.transaction.pay_transaction

import com.vixiloc.vcashiermobile.domain.model.TransactionResponse
data class PayTransactionState(
    val isLoading: Boolean = false,
    val error: String = "",
    val success: String = "",
    val transactionData : TransactionResponse? = null
)
