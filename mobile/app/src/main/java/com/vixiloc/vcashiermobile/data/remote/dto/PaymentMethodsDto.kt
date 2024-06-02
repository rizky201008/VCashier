package com.vixiloc.vcashiermobile.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.PaymentMethodData
import com.vixiloc.vcashiermobile.domain.model.PaymentMethods

data class PaymentMethodsDto(
    @SerializedName("data")
    val `data`: List<PaymentMethodDataDto>
)

data class PaymentMethodDataDto(
    @SerializedName("cash")
    val cash: Boolean,
    @SerializedName("code")
    val code: String,
    @SerializedName("created_at")
    val createdAt: Any?,
    @SerializedName("fee")
    val fee: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: Any?
)

fun PaymentMethodDataDto.toDomain(): PaymentMethodData {
    return PaymentMethodData(
        cash = cash,
        code = code,
        fee = fee,
        id = id,
        name = name
    )
}

fun PaymentMethodsDto.toDomain(): PaymentMethods {
    return PaymentMethods(
        data = data.map { it.toDomain() }
    )
}