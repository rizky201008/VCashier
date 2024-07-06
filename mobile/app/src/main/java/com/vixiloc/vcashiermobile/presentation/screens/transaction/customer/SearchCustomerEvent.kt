package com.vixiloc.vcashiermobile.presentation.screens.transaction.customer

import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem

sealed class SearchCustomerEvent {
    data class ShowErrorAlert(val show:Boolean): SearchCustomerEvent()
    data class ShowSuccessAlert(val show:Boolean): SearchCustomerEvent()
    data class SetSelectedCustomer(val customer: CustomerResponseItem): SearchCustomerEvent()
    data class SetSearchValue(val searchValue: String): SearchCustomerEvent()
}