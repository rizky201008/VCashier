package com.vixiloc.vcashiermobile.domain.model

import com.vixiloc.vcashiermobile.data.remote.dto.products.CreateUpdateProductRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.VariationDto

data class CreateUpdateProductRequest(
    val description: String,
    val id: Int?,
    val name: String,
    val categoryId: Int,
    val variations: List<Variation>
)

data class Variation(
    val price: Int,
    val priceGrocery: Int,
    val stock: Int,
    val unit: String,
    val id: Int? = null
)

fun Variation.toDto(): VariationDto {
    return VariationDto(
        price = price,
        priceGrocery = priceGrocery,
        stock = stock,
        unit = unit
    )
}

fun CreateUpdateProductRequest.toDto(): CreateUpdateProductRequestDto {
    return CreateUpdateProductRequestDto(
        description = description,
        id = id,
        name = name,
        categoryId = categoryId,
        variations = variations.map { it.toDto() }
    )
}