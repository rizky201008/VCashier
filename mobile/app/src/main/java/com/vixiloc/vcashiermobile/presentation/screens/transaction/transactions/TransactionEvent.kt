package com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions

import com.vixiloc.vcashiermobile.domain.model.products.ProductsResponseItems
import com.vixiloc.vcashiermobile.domain.model.transactions.TransactionsData

sealed class TransactionEvent {
    data object Refresh : TransactionEvent()
    data object DismissAlertMessage : TransactionEvent()
    data class OnSearchChanged(val query: String) : TransactionEvent()
    data class SelectStatus(val status: String) : TransactionEvent()
    data class ShowTransactionAction(val show: Boolean, val data: TransactionsData?) : TransactionEvent()
}