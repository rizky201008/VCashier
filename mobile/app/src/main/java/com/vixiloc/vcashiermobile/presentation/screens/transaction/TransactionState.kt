package com.vixiloc.vcashiermobile.presentation.screens.transaction

import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem
import com.vixiloc.vcashiermobile.domain.model.payments.PaymentMethodData
import com.vixiloc.vcashiermobile.domain.model.products.ProductResponseItems
import com.vixiloc.vcashiermobile.domain.model.transactions.TransactionsData

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
    ),
    val transactions: List<TransactionsData> = emptyList(),
    val searchStatus: Boolean = false,
    val searchResult: List<TransactionsData> = emptyList(),
    val searchQuery: String = "",
    val transactionTotal: Int = 0,
    val paymentAmount: Int = 0,
    val paymentMethods: List<PaymentMethodData> = emptyList(),
    val paymentMethod: PaymentMethodData? = null,
    val selectedTransactionId: String = "",
)