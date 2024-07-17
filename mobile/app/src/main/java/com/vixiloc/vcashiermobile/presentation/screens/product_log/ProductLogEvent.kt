package com.vixiloc.vcashiermobile.presentation.screens.product_log

sealed class ProductLogEvent {
    data class ShowSuccessAlert(val show: Boolean) : ProductLogEvent()
    data class ShowErrorAlert(val show: Boolean) : ProductLogEvent()
    data object Refresh : ProductLogEvent()
}