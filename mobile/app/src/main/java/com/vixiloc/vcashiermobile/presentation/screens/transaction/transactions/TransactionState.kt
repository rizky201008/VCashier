package com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions

import com.vixiloc.vcashiermobile.domain.model.products.ProductsResponseItems
import com.vixiloc.vcashiermobile.domain.model.transactions.TransactionsData

data class TransactionState(
    val products: List<ProductsResponseItems> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val success: String = "",
    val transactions: List<TransactionsData> = emptyList(),
    val searchQuery: String = "",
    val selectedTransaction: TransactionsData? = null,
    val showTransactionAction: Boolean = false,
    val status: String = "semua"
)