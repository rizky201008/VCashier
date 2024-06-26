package com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction

import com.vixiloc.vcashiermobile.domain.model.products.ProductResponseItems
import com.vixiloc.vcashiermobile.domain.model.products.ProductsVariation
import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems

sealed class CreateTransactionEvent {
    data object DismissAlertMessage : CreateTransactionEvent()
    data object Refresh : CreateTransactionEvent()
    data object ClearCart : CreateTransactionEvent()
    data class AddVariation(val variation: ProductsVariation, val product: ProductResponseItems) :
        CreateTransactionEvent()

    data object DismissAddToCartModal : CreateTransactionEvent()
    data class AddToCart(val item: CartItems) : CreateTransactionEvent()
}