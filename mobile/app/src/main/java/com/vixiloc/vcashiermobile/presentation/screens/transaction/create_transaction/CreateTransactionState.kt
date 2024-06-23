package com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction

import com.vixiloc.vcashiermobile.domain.model.products.ProductResponseItems

data class CreateTransactionState(
    val products: List<ProductResponseItems> = emptyList(),
    val selectedProduct: List<Map<ProductResponseItems, Int>> = emptyList(),
    val error: String = "",
    val success: String = "",
    val totalPrice: Int = 0,
    val isLoading: Boolean = false,
)
