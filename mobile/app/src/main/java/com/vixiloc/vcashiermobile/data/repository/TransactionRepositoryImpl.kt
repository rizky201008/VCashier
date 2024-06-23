package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.TransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.TransactionsResponseDto
import com.vixiloc.vcashiermobile.domain.repository.TransactionRepository

class TransactionRepositoryImpl(private val api: ApiService) : TransactionRepository {
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
}