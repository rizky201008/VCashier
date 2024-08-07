package com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction

import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem
import com.vixiloc.vcashiermobile.domain.model.products.ProductsResponseItems
import com.vixiloc.vcashiermobile.domain.model.products.ProductsVariation
import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems

sealed class CreateTransactionEvent {
    data object DismissAlertMessage : CreateTransactionEvent()
    data object Refresh : CreateTransactionEvent()
    data class AddVariation(val variation: ProductsVariation, val product: ProductsResponseItems) :
        CreateTransactionEvent()

    data object DismissAddToCartModal : CreateTransactionEvent()
    data class AddToCart(val item: CartItems) : CreateTransactionEvent()
    data class SelectCategory(val category: CategoriesResponseItem?) : CreateTransactionEvent()
    data class UpdateSearchValue(val value: String) : CreateTransactionEvent()
}