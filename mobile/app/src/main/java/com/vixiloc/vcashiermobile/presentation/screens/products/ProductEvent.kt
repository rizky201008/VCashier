package com.vixiloc.vcashiermobile.presentation.screens.products

import com.vixiloc.vcashiermobile.domain.model.ProductResponseItems


sealed class ProductEvent {
    data object DismissAlertMessage : ProductEvent()
//    data class InputProductName(val name: String) : ProductEvent()
//    data class InputProductNumber(val number: String) : ProductEvent()
//    data object SubmitCreateProduct : ProductEvent()
//    data object SubmitUpdateProduct : ProductEvent()
//    data class PreFillFormData(val id: Int?, val name: String, val number: String?) : ProductEvent()
//    data class DeleteProduct(val data: ProductResponseItems) : ProductEvent()
//    data object ProcessDeleteProduct : ProductEvent()
//    data class InputSearchValue(val query: String) : ProductEvent()
}