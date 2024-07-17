package com.vixiloc.vcashiermobile.presentation.screens.product_log.list_logs

import com.vixiloc.vcashiermobile.domain.model.product_logs.ProductLogsResponseData

data class ProductLogState(
    val isLoading: Boolean = false,
    val showAddLogDialog: Boolean = false,
    val error: String = "",
    val showError: Boolean = false,
    val success: String = "",
    val showSuccess: Boolean = false,
    val logs: List<ProductLogsResponseData> = emptyList()
)
