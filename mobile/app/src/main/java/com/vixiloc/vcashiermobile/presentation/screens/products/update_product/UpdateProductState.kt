package com.vixiloc.vcashiermobile.presentation.screens.products.update_product

import android.net.Uri
import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem
import com.vixiloc.vcashiermobile.domain.model.products.Variation


data class UpdateProductState(
    val isLoading: Boolean = false,
    val success: String = "",
    val showSuccess: Boolean = false,
    val error: String = "",
    val showError: Boolean = false,
    val productId: String = "",
    val productName: String = "",
    val productDescription: String = "",
    val productCategoryId: String = "",
    val productCategory: String = "",
    val productImageUri: Uri? = null,
    val productImage: String = "",
    val productVariations: List<Variation> = emptyList(),
    val editMode: Boolean = false,
    val variationName: String = "",
    val variationPrice: String = "",
    val variationPriceGrocery: String = "",
    val variationPriceCapital: String = "",
    val selectedVariationId: String = "",
    val showEditVariation: Boolean = false,
    val showSearchCategory: Boolean = false,
    val categories: List<CategoriesResponseItem> = emptyList()
)
