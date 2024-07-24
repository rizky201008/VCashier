package com.vixiloc.vcashiermobile.presentation.screens.products.update_product

import android.net.Uri
import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem
import com.vixiloc.vcashiermobile.domain.model.products.Variation

sealed class UpdateProductEvent {
    data class ShowError(val show: Boolean) : UpdateProductEvent()
    data class ShowSuccess(val show: Boolean) : UpdateProductEvent()
    data class ChangeInput(val name: FormInputName, val value: String) : UpdateProductEvent()
    data class ShowEditVariation(val show: Boolean) : UpdateProductEvent()
    data class SetProductId(val id: String) : UpdateProductEvent()
    data class SetProductImageUri(val uri: Uri?) : UpdateProductEvent()
    data object SaveEditProduct : UpdateProductEvent()
    data object SaveEditVariation : UpdateProductEvent()
    data object SaveEditImage : UpdateProductEvent()
    data object Refresh : UpdateProductEvent()
    data class SetEditMode(val editMode: Boolean) : UpdateProductEvent()
    data class GetProduct(val id: String) : UpdateProductEvent()
    data class SelectVariation(val variation: Variation) : UpdateProductEvent()
    data class ShowSearchCategory(val show: Boolean) : UpdateProductEvent()
    data class SelectCategory(val category: CategoriesResponseItem) : UpdateProductEvent()
}

enum class FormInputName {
    ProductName,
    ProductDescription,
    VariationName,
    VariationPrice,
    VariationPriceGrocery,
    VariationPriceCapital
}