package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.CreateTransactionRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateTransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.TransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.TransactionsResponseDto

interface TransactionRepository {

    suspend fun createTransaction(
        token: String,
        data: CreateTransactionRequestDto
    ): CreateTransactionResponseDto

    suspend fun getTransactions(token: String): TransactionsResponseDto
    suspend fun getTransaction(token: String, id: String): TransactionResponseDto
}