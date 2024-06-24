package com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction

import com.vixiloc.vcashiermobile.domain.model.products.ProductsVariation

sealed class CreateTransactionEvent {
    data object DismissAlertMessage : CreateTransactionEvent()
    data object Refresh : CreateTransactionEvent()
    data object ClearCart : CreateTransactionEvent()
    data class AddToCart(val variation: ProductsVariation) : CreateTransactionEvent()
    data object DismissAddToCartModal : CreateTransactionEvent()
}