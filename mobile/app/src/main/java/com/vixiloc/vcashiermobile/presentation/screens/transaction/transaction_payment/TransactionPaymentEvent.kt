package com.vixiloc.vcashiermobile.presentation.screens.transaction.transaction_payment

import com.vixiloc.vcashiermobile.domain.model.payments.PaymentMethodData

sealed class TransactionPaymentEvent {
    data object DismissAlertMessage : TransactionPaymentEvent()
    data class InsertTransactionTotal(val total: Int) : TransactionPaymentEvent()
    data class UpdatePaymentAmount(val amount: String) : TransactionPaymentEvent()
    data class SelectPaymentMethod(val method: PaymentMethodData) : TransactionPaymentEvent()
    data object SubmitTransactionPayment : TransactionPaymentEvent()
}