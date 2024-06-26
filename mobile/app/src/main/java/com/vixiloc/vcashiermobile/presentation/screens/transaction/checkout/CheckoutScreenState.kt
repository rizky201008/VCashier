package com.vixiloc.vcashiermobile.presentation.screens.transaction.checkout

import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem
import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems

data class CheckoutScreenState(
    val cartItems: List<CartItems> = emptyList(),
    val customer: CustomerResponseItem? = null,
    val isLoading: Boolean = false,
    val error: String = "",
    val success: String = "",
    val transactionId: String = "",
    val validationErrors: List<String> = emptyList()
)