package com.vixiloc.vcashiermobile.presentation.screens.transaction.process_payment

import com.vixiloc.vcashiermobile.domain.model.payments.PaymentMethodData

sealed class ProcessPaymentEvent {
    data object DismissAlertMessage : ProcessPaymentEvent()
    data class InsertTransactionTotal(val total: Int) : ProcessPaymentEvent()
    data class UpdatePaymentAmount(val amount: String) : ProcessPaymentEvent()
    data class SelectPaymentMethod(val method: PaymentMethodData) : ProcessPaymentEvent()
    data object SubmitTransactionPayment : ProcessPaymentEvent()
}