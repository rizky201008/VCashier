package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.PaymentMethodsDto
import com.vixiloc.vcashiermobile.domain.repository.PaymentsRepository

class PaymentsRepositoryImpl(private val api: ApiService) : PaymentsRepository {
    override suspend fun getPaymentMethods(token: String): PaymentMethodsDto {
        return api.getPaymentMethods(token = "Bearer $token")
    }
}