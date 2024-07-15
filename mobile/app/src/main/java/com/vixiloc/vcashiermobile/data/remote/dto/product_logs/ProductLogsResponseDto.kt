package com.vixiloc.vcashiermobile.data.remote.dto.product_logs


import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.data.remote.dto.products.CategoryDto
import com.vixiloc.vcashiermobile.domain.model.product_logs.ProductLogsProduct
import com.vixiloc.vcashiermobile.domain.model.product_logs.ProductLogsProductVariation
import com.vixiloc.vcashiermobile.domain.model.product_logs.ProductLogsResponse
import com.vixiloc.vcashiermobile.domain.model.product_logs.ProductLogsResponseData
import com.vixiloc.vcashiermobile.domain.model.product_logs.ProductLogsUsers

data class ProductLogsResponseDto(
    @SerializedName("data")
    val `data`: List<ProductLogsResponseDataDto>
)

data class ProductLogsResponseDataDto(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("information")
    val information: String,
    @SerializedName("product_variation_id")
    val productVariationId: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("product_variation")
    val productVariation: ProductLogsProductVariationDto,
    @SerializedName("user")
    val user: ProductLogsUsersDto
)

data class ProductLogsProductVariationDto(
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
    val productId: Int,
    @SerializedName("product")
    val product: ProductLogsProductDto
)

data class ProductLogsProductDto(

    @SerializedName("category_id")
    val categoryId: Int,

    @SerializedName("image_path")
    val imagePath: String? = null,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("id")
    val id: Int
)

data class ProductLogsUsersDto(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

fun ProductLogsProductDto.toDomain() = ProductLogsProduct(
    categoryId = this.categoryId,
    description = this.description,
    id = this.id,
    imagePath = this.imagePath,
    imageUrl = this.imageUrl,
    name = this.name
)

fun ProductLogsProductVariationDto.toDomain() = ProductLogsProductVariation(
    id = this.id,
    price = this.price,
    priceGrocery = this.priceGrocery,
    stock = this.stock,
    unit = this.unit,
    productId = this.productId,
    product = this.product.toDomain()
)

fun ProductLogsUsersDto.toDomain() = ProductLogsUsers(
    email = this.email,
    id = this.id,
    name = this.name,
    role = this.role
)

fun ProductLogsResponseDataDto.toDomain() = ProductLogsResponseData(
    amount = this.amount,
    createdAt = this.createdAt,
    id = this.id,
    information = this.information,
    productVariationId = this.productVariationId,
    type = this.type,
    userId = this.userId,
    productVariation = this.productVariation.toDomain(),
    user = this.user.toDomain()
)

fun ProductLogsResponseDto.toDomain(): ProductLogsResponse {
    return ProductLogsResponse(
        data = this.data.map { it.toDomain() }
    )
}