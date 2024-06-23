package com.vixiloc.vcashiermobile.data.remote.dto.products

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.products.Category
import com.vixiloc.vcashiermobile.domain.model.products.Product
import com.vixiloc.vcashiermobile.domain.model.products.ProductResponseItems
import com.vixiloc.vcashiermobile.domain.model.products.ProductsResponse

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

    @field:SerializedName("price_grocery")
    val priceGrocery: Int? = null,

    @field:SerializedName("unit")
    val unit: String,

    @field:SerializedName("product")
    val product: ProductDto,

    @field:SerializedName("price")
    val price: Int,

    @field:SerializedName("product_id")
    val productId: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("stock")
    val stock: Int
)

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
        priceGrocery = priceGrocery,
        unit = unit,
        product = product.toDomain(),
        price = price,
        id = id,
        stock = stock
    )
}

fun ProductsResponseDto.toProductsResponse(): ProductsResponse {
    return ProductsResponse(
        data = data.map {
            it.toProductResponseItems()
        }
    )
}