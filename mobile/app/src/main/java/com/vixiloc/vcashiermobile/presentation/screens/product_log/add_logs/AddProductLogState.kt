package com.vixiloc.vcashiermobile.presentation.screens.product_log.add_logs

import com.vixiloc.vcashiermobile.domain.model.products.ProductsResponseItems
import com.vixiloc.vcashiermobile.domain.model.products.ProductsVariation

data class AddProductLogState(
    val logInfo: String = "",
    val logInfoError: String = "",
    val logAmount: String = "",
    val logAmountError: String = "",
    val logType: String = "increase",
    val logProductVariation: ProductsVariation? = null,
    val products: List<ProductsResponseItems> = emptyList(),
    val isLoading: Boolean = false,
    val showAddLogDialog: Boolean = false,
    val error: String = "",
    val showError: Boolean = false,
    val success: String = "",
    val showSuccess: Boolean = false,
    val searchQuery: String = ""
)
