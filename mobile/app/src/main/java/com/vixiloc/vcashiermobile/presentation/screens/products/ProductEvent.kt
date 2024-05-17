package com.vixiloc.vcashiermobile.presentation.screens.products

import android.net.Uri


sealed class ProductEvent {
    data object DismissAlertMessage : ProductEvent()
    data class InputProductName(val name: String) : ProductEvent()
    data class InputProductDescription(val description: String) : ProductEvent()
    data class InputProductCategory(val category: Int) : ProductEvent()
    data class InputVariationUnit(val unit: String) : ProductEvent()
    data class InputVariationPrice(val price: String) : ProductEvent()
    data class InputVariationPriceGrocery(val price: String) : ProductEvent()
    data class InputVariationStock(val stock: String) : ProductEvent()
    data class InputProductImage(val image: Uri?) : ProductEvent()
    data class InputProductCategoryName(val name: String) : ProductEvent()

    data object ToggleVariationDialog : ProductEvent()
    data object SubmitAddVariation : ProductEvent()

    data object SubmitCreateProduct : ProductEvent()
//    data object SubmitUpdateProduct : ProductEvent()
//    data class PreFillFormData(val id: Int?, val name: String, val number: String?) : ProductEvent()
//    data class DeleteProduct(val data: ProductResponseItems) : ProductEvent()
//    data object ProcessDeleteProduct : ProductEvent()
//    data class InputSearchValue(val query: String) : ProductEvent()
}