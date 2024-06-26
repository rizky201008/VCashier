package com.vixiloc.vcashiermobile.data.remote.dto.products

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.products.Category
import com.vixiloc.vcashiermobile.domain.model.products.Product
import com.vixiloc.vcashiermobile.domain.model.products.ProductResponseItems
import com.vixiloc.vcashiermobile.domain.model.products.ProductsResponse
import com.vixiloc.vcashiermobile.domain.model.products.ProductsVariation

data class ProductsResponseDto(

    @field:SerializedName("data")
    val data: List<ProductResponseItemsDto> = emptyList()
)

data class CategoryDto(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)

data class ProductDto(

    @field:SerializedName("category_id")
    val categoryId: Int,

    @field:SerializedName("image_path")
    val imagePath: String? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("category")
    val category: CategoryDto
)

data class ProductResponseItemsDto(
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
    val variations: List<ProductsVariationDto>
)

data class ProductsVariationDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("price_grocery")
    val priceGrocery: Int,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("product_id")
    val productId: Int
)

fun ProductsVariationDto.toDomain(): ProductsVariation {
    return ProductsVariation(
        id = id,
        price = price,
        priceGrocery = priceGrocery,
        stock = stock,
        unit = unit,
        productId = productId
    )
}

fun ProductDto.toDomain(): Product {
    return Product(
        imageUrl = imageUrl,
        name = name,
        description = description,
        id = id,
        category = category.toDomain()
    )
}

fun CategoryDto.toDomain(): Category {
    return Category(
        name = name,
        id = id
    )
}

fun ProductResponseItemsDto.toProductResponseItems(): ProductResponseItems {
    return ProductResponseItems(
        name = name,
        imageUrl = imageUrl,
        description = description,
        id = id,
        category = category.toDomain(),
        variations = variations.map {
            it.toDomain()
        }
    )
}

fun ProductsResponseDto.toDomain(): ProductsResponse {
    return ProductsResponse(
        data = data.map {
            it.toProductResponseItems()
        }
    )
}