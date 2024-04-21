package com.vixiloc.vcashiermobile.presentation.screens.transaction

import com.vixiloc.vcashiermobile.domain.model.ProductResponseItems

data class TransactionState(
    val products: List<ProductResponseItems> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)