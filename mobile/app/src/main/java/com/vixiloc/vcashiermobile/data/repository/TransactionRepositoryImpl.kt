package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.local.realm.CartItemsDao
import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.ReportsResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.TransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.TransactionsResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.UpdateTransactionRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.UpdateTransactionResponseDto
import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems
import com.vixiloc.vcashiermobile.domain.model.transactions.UpdateTransactionResponse
import com.vixiloc.vcashiermobile.domain.repository.TransactionRepository
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(
    private val api: ApiService,
    private val cartItemsDao: CartItemsDao
) :
    TransactionRepository {
    override suspend fun createTransaction(
        token: String,
        data: CreateTransactionRequestDto
    ): CreateTransactionResponseDto {
        return api.createTransaction("Bearer $token", data)
    }

    override suspend fun getTransactions(
        token: String, status: String,
        paymentStatus: String
    ): TransactionsResponseDto {
        return api.getTransactions("Bearer $token", status, paymentStatus)
    }

    override suspend fun getTransaction(token: String, id: String): TransactionResponseDto {
        return api.getTransaction(token = "Bearer $token", id = id)
    }

    override suspend fun updateCartItems(cartItems: CartItems) {
        cartItemsDao.updateCartItems(cartItems)
    }

    override suspend fun deleteCartItems(cartItems: CartItems) {
        cartItemsDao.deleteCartItems(cartItems)
    }

    override suspend fun clearCartItems() {
        cartItemsDao.clearCartItems()
    }

    override suspend fun updateTransaction(
        token: String,
        data: UpdateTransactionRequestDto
    ): UpdateTransactionResponseDto {
        return api.updateTransaction(token = "Bearer $token", data = data)
    }

    override suspend fun reportTransaction(token: String): ReportsResponseDto {
        return api.getTransactionReport(token = "Bearer $token")
    }

    override suspend fun addToCart(data: CartItems) {
        cartItemsDao.insertCartItems(data)
    }

    override suspend fun getCartItems(): Flow<List<CartItems>> {
        return cartItemsDao.getCartItems()
    }
}