package com.vixiloc.vcashiermobile.presentation.screens.products

import com.vixiloc.vcashiermobile.domain.model.ProductResponseItems

data class ProductState(
    val products: List<ProductResponseItems> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val success: String = "",
)