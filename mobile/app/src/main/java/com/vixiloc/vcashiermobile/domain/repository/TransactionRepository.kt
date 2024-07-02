package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.TransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.TransactionsResponseDto
import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun createTransaction(
        token: String,
        data: CreateTransactionRequestDto
    ): CreateTransactionResponseDto

    suspend fun getTransactions(
        token: String,
        status: String = "",
        paymentStatus: String = ""
    ): TransactionsResponseDto

    suspend fun getTransaction(token: String, id: String): TransactionResponseDto
    suspend fun addToCart(data: CartItems)
    suspend fun getCartItems(): Flow<List<CartItems>>
    suspend fun updateCartItems(cartItems: CartItems)
    suspend fun deleteCartItems(cartItems: CartItems)
    suspend fun clearCartItems()
}