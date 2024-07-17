package com.vixiloc.vcashiermobile.presentation.screens.product_log.add_logs

import com.vixiloc.vcashiermobile.domain.model.products.ProductsVariation

sealed class AddProductLogEvent {
    data class ChangeInput(val name: InputName, val value: String) : AddProductLogEvent()
    data class ShowAddLogDialog(val show: Boolean) : AddProductLogEvent()
    data class SelectVariation(val variation: ProductsVariation?) : AddProductLogEvent()
    data object AddProductLog : AddProductLogEvent()
    data class SearchProduct(val query: String) : AddProductLogEvent()
}

enum class InputName {
    Info,
    Amount,
    Type
}