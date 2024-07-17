package com.vixiloc.vcashiermobile.data.remote.dto.product_logs

import com.google.gson.annotations.SerializedName

data class CreateProductLogsRequestDto(
    @SerializedName("information")
    val information: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("product_variation_id")
    val product_variation_id: Int
)
