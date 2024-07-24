package com.vixiloc.vcashiermobile.presentation.screens.products.list_products

import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem


sealed class ProductEvent {
    data class ShowErrorAlert(val show: Boolean) : ProductEvent()
    data class ShowSuccessAlert(val show: Boolean) : ProductEvent()
    data class ShowMessageAlert(val show: Boolean) : ProductEvent()
    data class SelectCategory(val category: CategoriesResponseItem?) : ProductEvent()
    data object Refresh : ProductEvent()
}