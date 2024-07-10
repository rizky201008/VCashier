package com.vixiloc.vcashiermobile.presentation.screens.products.create_product

import android.net.Uri
import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem
import com.vixiloc.vcashiermobile.domain.model.products.Variation

data class CreateProductState(
    val isLoading: Boolean = false,
    val error: String = "",
    val showError: Boolean = false,
    val showSuccess: Boolean = false,
    val success: String = "",
    val productName: String = "",
    val productDescription: String = "",
    val selectedCategory: CategoriesResponseItem? = null,
    val variations: List<Variation> = emptyList(),
    val image: Uri? = null,
    val productId: Int? = null,
    val variationPrice: String = "",
    val variationPriceCapital: String = "",
    val variationPriceGrocery: String = "",
    val variationStock: String = "",
    val variationUnit: String = "",
    val variationId: Int? = null,
    val showAddVariation: Boolean = false,
    val categories: List<CategoriesResponseItem> = emptyList(),
    val alsoUpdateImage: Boolean = false
)
