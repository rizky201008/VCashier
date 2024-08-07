package com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction

import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem
import com.vixiloc.vcashiermobile.domain.model.products.ProductsResponseItems
import com.vixiloc.vcashiermobile.domain.model.products.ProductsVariation
import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems

data class CreateTransactionState(
    val products: List<ProductsResponseItems> = emptyList(),
    val selectedVariation: ProductsVariation? = null,
    val error: String = "",
    val success: String = "",
    val isLoading: Boolean = false,
    val cartItems: List<CartItems> = emptyList(),
    val cartTotal: Int = 0,
    val selectedProduct: ProductsResponseItems? = null,
    val categories: List<CategoriesResponseItem> = emptyList(),
    val selectedCategory: CategoriesResponseItem? = null,
    val searchQuery: String = ""
)
