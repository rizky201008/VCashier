package com.vixiloc.vcashiermobile.presentation.screens.transaction

import com.vixiloc.vcashiermobile.domain.model.CustomerResponseItem
import com.vixiloc.vcashiermobile.domain.model.ProductResponseItems

data class TransactionState(
    val products: List<ProductResponseItems> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val success: String = "",
    val selectedProduct: List<Map<ProductResponseItems, Int>> = emptyList(),
    val totalPrice: Int = 0,
    val customers: List<CustomerResponseItem> = emptyList(),
    val customer: CustomerResponseItem = CustomerResponseItem(
        id = 1,
        name = "Tamu",
        phoneNumber = "-"
    )
)