package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.payments.MakePaymentRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.MakePaymentResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.PaymentMethodsDto
import com.vixiloc.vcashiermobile.domain.repository.PaymentsRepository

class PaymentsRepositoryImpl(private val api: ApiService) : PaymentsRepository {
    override suspend fun getPaymentMethods(token: String): PaymentMethodsDto {
        return api.getPaymentMethods(token = "Bearer $token")
    }

    override suspend fun makePayment(
        token: String,
        data: MakePaymentRequestDto
    ): MakePaymentResponseDto {
        return api.makePayment(token = "Bearer $token", data = data)
    }
}