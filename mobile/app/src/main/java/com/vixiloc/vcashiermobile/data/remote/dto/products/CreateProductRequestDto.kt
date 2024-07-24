package com.vixiloc.vcashiermobile.data.remote.dto.products


import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.products.Variation

data class CreateProductRequestDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("variations")
    val variations: List<VariationDto>
)

data class VariationDto(
    @SerializedName("price")
    val price: Int,
    @SerializedName("price_grocery")
    val priceGrocery: Int,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("price_capital")
    val priceCapital: Int
)

fun VariationDto.toDomain(): Variation {
    return Variation(
        price = price,
        priceGrocery = priceGrocery,
        stock = stock,
        unit = unit,
        id = id,
        priceCapital = priceCapital
    )
}