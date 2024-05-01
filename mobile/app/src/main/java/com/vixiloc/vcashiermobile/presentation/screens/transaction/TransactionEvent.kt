package com.vixiloc.vcashiermobile.presentation.screens.transaction

import com.vixiloc.vcashiermobile.domain.model.ProductResponseItems

sealed class TransactionEvent {
    data class ToggleError(val error: String) : TransactionEvent()
    data object Refresh : TransactionEvent()

    data class SelectProduct(val product: ProductResponseItems) : TransactionEvent()
}