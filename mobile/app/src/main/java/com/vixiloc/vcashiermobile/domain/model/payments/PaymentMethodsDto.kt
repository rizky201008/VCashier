package com.vixiloc.vcashiermobile.domain.model.payments


import com.google.gson.annotations.SerializedName

data class PaymentMethods(
    @SerializedName("data")
    val `data`: List<PaymentMethodData>
)

data class PaymentMethodData(
    @SerializedName("cash")
    val cash: Boolean,
    @SerializedName("code")
    val code: String,
    @SerializedName("fee")
    val fee: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)