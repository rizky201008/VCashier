package com.vixiloc.vcashiermobile.presentation.screens.products

import android.net.Uri
import com.vixiloc.vcashiermobile.domain.model.ProductResponseItems
import com.vixiloc.vcashiermobile.domain.model.Variation

data class ProductState(
    val products: List<ProductResponseItems> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val success: String = "",
    val productName: String = "",
    val productDescription: String = "",
    val productCategory: Int = 0,
    val variations: List<Variation> = emptyList(),
    val image: Uri? = null,
    val categoryName: String = "",
    val productId: Int? = null,
    val variationPrice: String = "",
    val variationPriceGrocery: String = "",
    val variationStock: String = "",
    val variationUnit: String = "",
    val variationId: Int? = null,
    val showVariationDialog: Boolean = false,
    val categories: List<Map<String, Int>> = emptyList(),
    val alsoUpdateImage: Boolean = false
)