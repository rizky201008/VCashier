package com.vixiloc.vcashiermobile.presentation.screens.transaction.pay_transaction

sealed class PayTransactionEvent {
    data object DismissAlertMessage : PayTransactionEvent()
    data class GetTransaction(val id: String) : PayTransactionEvent()
}