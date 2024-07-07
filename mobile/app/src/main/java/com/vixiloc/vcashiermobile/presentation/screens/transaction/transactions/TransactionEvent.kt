package com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions

import com.vixiloc.vcashiermobile.domain.model.products.ProductResponseItems

sealed class TransactionEvent {
    data object Refresh : TransactionEvent()
    data class SelectProduct(val product: ProductResponseItems) : TransactionEvent()
    data object DismissAlertMessage : TransactionEvent()
    data class OnSearchChanged(val query: String) : TransactionEvent()
    data class SelectTransaction(val id: String) : TransactionEvent()
    data class SelectStatus(val status: String) : TransactionEvent()
}