package com.vixiloc.vcashiermobile.presentation.screens.products.list_products

import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem
import com.vixiloc.vcashiermobile.domain.model.products.ProductsResponseItems

data class ProductState(
    val products: List<ProductsResponseItems> = emptyList(),
    val categories: List<CategoriesResponseItem> = emptyList(),
    val selectedCategory: CategoriesResponseItem = CategoriesResponseItem(
        id = 0,
        name = "Semua Kategori"
    ),
    val isLoading: Boolean = false,
    val error: String = "",
    val showError: Boolean = false,
    val success: String = "",
    val showSuccess: Boolean = false,
    val showMessage: Boolean = false
)