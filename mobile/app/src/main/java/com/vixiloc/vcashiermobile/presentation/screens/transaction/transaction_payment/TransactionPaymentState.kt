package com.vixiloc.vcashiermobile.presentation.screens.transaction.transaction_payment

import com.vixiloc.vcashiermobile.domain.model.PaymentMethodData

data class TransactionPaymentState (
    val isLoading: Boolean = false,
    val error: String = "",
    val success: String = "",
    val totalPrice: Int = 0,
    val transactionTotal: Int = 0,
    val paymentAmount: Int = 0,
    val paymentMethods: List<PaymentMethodData> = emptyList(),
    val paymentMethod: PaymentMethodData? = null,
    val selectedTransactionId: String = "",
)