package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.TransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.TransactionsResponseDto

interface TransactionRepository {

    suspend fun createTransaction(
        token: String,
        data: CreateTransactionRequestDto
    ): CreateTransactionResponseDto

    suspend fun getTransactions(token: String): TransactionsResponseDto
    suspend fun getTransaction(token: String, id: String): TransactionResponseDto
}