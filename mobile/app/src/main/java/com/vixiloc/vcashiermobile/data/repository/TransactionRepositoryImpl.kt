package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.local.realm.CartItemsDao
import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.TransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.TransactionsResponseDto
import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems
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

    override suspend fun getTransactions(token: String): TransactionsResponseDto {
        return api.getTransactions("Bearer $token")
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

    override suspend fun addToCart(data: CartItems) {
        cartItemsDao.insertCartItems(data)
    }

    override suspend fun getCartItems(): Flow<List<CartItems>> {
        return cartItemsDao.getCartItems()
    }
}