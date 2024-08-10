package com.vixiloc.vcashiermobile.presentation.screens.transaction.process_payment

import com.vixiloc.vcashiermobile.domain.model.payments.MakePaymentResponse
import com.vixiloc.vcashiermobile.domain.model.payments.PaymentMethodData

data class ProcessPaymentState(
    val isLoading: Boolean = false,
    val error: String = "",
    val success: String = "",
    val transactionTotal: Int = 0,
    val change: Int = 0,
    val response: MakePaymentResponse? = null,
    val totalAmount: Int = 0,
    val paymentAmount: String = "",
    val paymentAmountError: String = "",
    val paymentMethods: List<PaymentMethodData> = emptyList(),
    val paymentMethod: PaymentMethodData? = null,
    val selectedTransactionId: String = "",
)