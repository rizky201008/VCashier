package com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions

import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem
import com.vixiloc.vcashiermobile.domain.model.payments.PaymentMethodData
import com.vixiloc.vcashiermobile.domain.model.products.ProductResponseItems

sealed class TransactionEvent {
    data object Refresh : TransactionEvent()
    data object ClearCart : TransactionEvent()
    data class SelectProduct(val product: ProductResponseItems) : TransactionEvent()
    data class IncreaseQty(val product: ProductResponseItems) : TransactionEvent()
    data class DecreaseQty(val product: ProductResponseItems) : TransactionEvent()
    data class UpdateCustomer(val customer: CustomerResponseItem?) : TransactionEvent()
    data object DismissAlertMessage : TransactionEvent()
    data object SubmitTransaction : TransactionEvent()
    data class InsertSelectedProducts(val products: List<Map<ProductResponseItems, Int>>) :
        TransactionEvent()

    data class SearchQueryChanged(val query: String) : TransactionEvent()
    data class SearchStatusChanged(val status: Boolean) : TransactionEvent()
    data class InsertTransactionTotal(val total: Int) : TransactionEvent()
    data class UpdatePaymentAmount(val amount: String) : TransactionEvent()
    data class SelectPaymentMethod(val method: PaymentMethodData) : TransactionEvent()
    data class SelectTransaction(val id: String) : TransactionEvent()
    data object SubmitTransactionPayment : TransactionEvent()
    data class SelectStatus(val status: String) : TransactionEvent()
}