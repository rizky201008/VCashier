package com.vixiloc.vcashiermobile.data.remote.dto.products

import com.google.gson.annotations.SerializedName

data class UpdateProductVariationRequestDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("price_grocery")
    val priceGrocery: Int,
    @SerializedName("price_capital")
    val priceCapital: Int,
)
