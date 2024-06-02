package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.PaymentMethodsDto

interface PaymentsRepository {

    suspend fun getPaymentMethods(token: String): PaymentMethodsDto
}