package com.vixiloc.vcashiermobile.presentation.screens.transaction.customer

import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem

data class SearchCustomerState(
    val searchValue: String = "",
    val selectedCustomer: CustomerResponseItem? = null,
    val customers: List<CustomerResponseItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val success: String = "",
    val showError: Boolean = false,
    val showSuccess: Boolean = false
)
