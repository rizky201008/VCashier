package com.vixiloc.vcashiermobile.data.remote.dto.products


import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.ProductResponse

data class ProductResponseDto(
    @SerializedName("category")
    val category: CategoryDto,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_path")
    val imagePath: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("variations")
    val variations: List<VariationDto>
)

fun ProductResponseDto.toDomain(): ProductResponse {
    return ProductResponse(
        category = category.toDomain(),
        categoryId = categoryId,
        description = description,
        id = id,
        imagePath = imagePath,
        imageUrl = imageUrl,
        name = name,
        variations = variations.map { it.toDomain() }
    )
}