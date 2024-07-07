package com.vixiloc.vcashiermobile.presentation.screens.customer

import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem

sealed class CustomerEvent {
    data class ShowError(val show: Boolean) : CustomerEvent()
    data class ShowSuccess(val show: Boolean) : CustomerEvent()
    data class ChangeInput(val type: CustomerFormInput, val value: String) : CustomerEvent()
    data object CreateCustomer : CustomerEvent()
    data object UpdateCustomer : CustomerEvent()
    data class FillFormData(val data: CustomerResponseItem?) : CustomerEvent()

    data class InputSearchValue(val query: String) : CustomerEvent()
    data class ShowCreateModal(val show: Boolean) : CustomerEvent()
    data class ShowUpdateModal(val show: Boolean) : CustomerEvent()
}

enum class CustomerFormInput {
    NAME, NUMBER
}

