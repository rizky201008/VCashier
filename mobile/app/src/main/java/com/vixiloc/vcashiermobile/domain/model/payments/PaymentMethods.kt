package com.vixiloc.vcashiermobile.domain.model.payments

data class PaymentMethods(
    val `data`: List<PaymentMethodData>
)

data class PaymentMethodData(
    val cash: Boolean,
    val code: String,
    val fee: Int,
    val id: Int,
    val name: String
)