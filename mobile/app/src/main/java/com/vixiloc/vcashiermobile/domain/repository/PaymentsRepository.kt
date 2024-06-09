package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.MakePaymentRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.MakePaymentResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.PaymentMethodsDto

interface PaymentsRepository {

    suspend fun getPaymentMethods(token: String): PaymentMethodsDto
    suspend fun makePayment(token: String, data: MakePaymentRequestDto): MakePaymentResponseDto
}