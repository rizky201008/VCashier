package com.vixiloc.vcashiermobile.presentation.screens.customer

import com.vixiloc.vcashiermobile.domain.model.CustomerResponseItem

sealed class CustomerEvent {
    data object DismissAlertMessage : CustomerEvent()
    data class InputCustomerName(val name: String) : CustomerEvent()
    data object SubmitCreateCustomer : CustomerEvent()
    data object SubmitUpdateCustomer : CustomerEvent()
    data class PreFillFormData(val id: Int, val name: String) : CustomerEvent()
    data class DeleteCustomer(val data: CustomerResponseItem) : CustomerEvent()
    data object ProcessDeleteCustomer : CustomerEvent()
    data class InputSearchValue(val query: String) : CustomerEvent()
}