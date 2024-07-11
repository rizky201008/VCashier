package com.vixiloc.vcashiermobile.presentation.screens.products.create_product

import android.net.Uri
import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem
import com.vixiloc.vcashiermobile.domain.model.products.Variation

sealed class CreateProductEvent {
    data class ShowAddVariationDialog(val show: Boolean) : CreateProductEvent()
    data class ShowErrorMessage(val show: Boolean) : CreateProductEvent()
    data class ShowSuccessMessage(val show: Boolean) : CreateProductEvent()
    data object AddVariation : CreateProductEvent()
    data class ChangeInput(val inputName: InputName, val value: String) : CreateProductEvent()
    data class SelectCategory(val category: CategoriesResponseItem?) : CreateProductEvent()
    data object CreateProduct : CreateProductEvent()
    data class ChangeImage(val image: Uri?) : CreateProductEvent()
    data class ShowEditVariationDialog(val show: Boolean) : CreateProductEvent()
    data object DeleteVariation : CreateProductEvent()
    data class ShowDeleteVariationDialog(val show: Boolean) : CreateProductEvent()
    data object UpdateVariation : CreateProductEvent()
    data class SelectVariation(val data: Variation) : CreateProductEvent()
}

enum class InputName {
    PRODUCT_NAME,
    PRODUCT_DESCRIPTION,
    VARIATION_PRICE,
    VARIATION_PRICE_CAPITAL,
    VARIATION_PRICE_GROCERY,
    VARIATION_STOCK,
    VARIATION_UNIT
}