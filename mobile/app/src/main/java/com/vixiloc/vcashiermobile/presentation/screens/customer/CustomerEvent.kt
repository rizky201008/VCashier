package com.vixiloc.vcashiermobile.presentation.screens.customer

sealed class CustomerEvent {
    data class ShowError(val show: Boolean) : CustomerEvent()
    data class ShowSuccess(val show: Boolean) : CustomerEvent()
    data class InputCustomerName(val name: String) : CustomerEvent()
    data class InputCustomerNumber(val number: String) : CustomerEvent()
    data object SubmitCreateCustomer : CustomerEvent()
    data object SubmitUpdateCustomer : CustomerEvent()
    data class PreFillFormData(val id: Int?, val name: String, val number: String?) :
        CustomerEvent()
    data class InputSearchValue(val query: String) : CustomerEvent()
}