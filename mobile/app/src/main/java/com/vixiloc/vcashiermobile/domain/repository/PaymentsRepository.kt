package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.payments.CreateVaRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.CreateVaResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.MakePaymentRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.MakePaymentResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.PaymentMethodsDto

interface PaymentsRepository {

    suspend fun getPaymentMethods(token: String): PaymentMethodsDto
    suspend fun makePayment(token: String, data: MakePaymentRequestDto): MakePaymentResponseDto
    suspend fun createVa(token: String, data: CreateVaRequestDto): CreateVaResponseDto
}