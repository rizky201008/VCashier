package com.vixiloc.vcashiermobile.domain.model.products

import com.vixiloc.vcashiermobile.data.remote.dto.products.UpdateProductVariationRequestDto

data class UpdateProductVariationRequest(
    val id: Int,
    val unit: String,
    val price: Int,
    val priceGrocery: Int,
    val priceCapital: Int,
)

fun UpdateProductVariationRequest.toDto() = UpdateProductVariationRequestDto(
    id = id,
    unit = unit,
    price = price,
    priceGrocery = priceGrocery,
    priceCapital = priceCapital,
)
