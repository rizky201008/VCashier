package com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction

import com.vixiloc.vcashiermobile.domain.model.ProductResponseItems

sealed class CreateTransactionEvent {
    data object DismissAlertMessage : CreateTransactionEvent()
    data class SelectProduct(val product: ProductResponseItems) : CreateTransactionEvent()

    data object Refresh : CreateTransactionEvent()
    data object ClearCart : CreateTransactionEvent()
}