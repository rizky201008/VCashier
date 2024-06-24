package com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction

import com.vixiloc.vcashiermobile.domain.model.products.ProductResponseItems
import com.vixiloc.vcashiermobile.domain.model.products.ProductsVariation

data class CreateTransactionState(
    val products: List<ProductResponseItems> = emptyList(),
    val selectedProduct: ProductsVariation? = null,
    val error: String = "",
    val success: String = "",
    val isLoading: Boolean = false,
)
