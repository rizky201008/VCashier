package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.CreateTransactionRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateTransactionResponseDto

interface TransactionRepository {

    suspend fun createTransaction(
        token: String,
        data: CreateTransactionRequestDto
    ): CreateTransactionResponseDto
}