package com.vixiloc.vcashiermobile.presentation.screens.transaction.checkout

import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem
import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems

sealed class CheckoutScreenEvent {
    data class UpdateCustomer(val customer: CustomerResponseItem?) : CheckoutScreenEvent()
    data class UpdateCart(val cartItems: CartItems) : CheckoutScreenEvent()
    data class DeleteCartItem(val data: CartItems) : CheckoutScreenEvent()
    data object CreateTransaction : CheckoutScreenEvent()
    data object DismissErrorAlert : CheckoutScreenEvent()
    data object DismissSuccessAlert : CheckoutScreenEvent()
}