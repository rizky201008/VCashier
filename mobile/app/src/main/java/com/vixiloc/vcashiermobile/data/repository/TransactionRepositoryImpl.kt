package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.CreateTransactionRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateTransactionResponseDto
import com.vixiloc.vcashiermobile.domain.repository.TransactionRepository

class TransactionRepositoryImpl(private val api: ApiService) : TransactionRepository {
    override suspend fun createTransaction(
        token: String,
        data: CreateTransactionRequestDto
    ): CreateTransactionResponseDto {
        return api.createTransaction("Bearer $token", data)
    }
}