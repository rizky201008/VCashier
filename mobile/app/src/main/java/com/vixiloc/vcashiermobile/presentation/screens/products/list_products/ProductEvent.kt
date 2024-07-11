package com.vixiloc.vcashiermobile.presentation.screens.products.list_products

import android.net.Uri
import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem
import com.vixiloc.vcashiermobile.domain.model.products.Variation


sealed class ProductEvent {
    data class ShowErrorAlert(val show: Boolean) : ProductEvent()
    data class ShowSuccessAlert(val show: Boolean) : ProductEvent()
    data class ShowMessageAlert(val show: Boolean) : ProductEvent()
    data class SelectCategory(val category: CategoriesResponseItem?) : ProductEvent()
}