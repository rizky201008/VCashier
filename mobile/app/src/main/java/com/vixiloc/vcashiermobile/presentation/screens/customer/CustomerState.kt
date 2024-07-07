package com.vixiloc.vcashiermobile.presentation.screens.customer

import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem

data class CustomerState(
    val isLoading: Boolean = false,
    val customers: List<CustomerResponseItem> = emptyList(),
    val error: String = "",
    val showError: Boolean = false,
    val showSuccess: Boolean = false,
    val customerName: String = "",
    val success: String = "",
    val customerId: Int? = null,
    val customerNumber: String? = null,
    val searchQuery: String = ""
)
