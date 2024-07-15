package com.vixiloc.vcashiermobile.domain.model.product_logs


data class ProductLogsResponse(
    val `data`: List<ProductLogsResponseData>
)

data class ProductLogsResponseData(
    val amount: Int,
    val createdAt: String,
    val id: Int,
    val information: String,
    val productVariationId: Int,
    val type: String,
    val userId: Int,
    val productVariation: ProductLogsProductVariation,
    val user: ProductLogsUsers
)

data class ProductLogsProductVariation(
    val id: Int,
    val price: Int,
    val priceGrocery: Int,
    val stock: Int,
    val unit: String,
    val productId: Int,
    val product: ProductLogsProduct
)

data class ProductLogsProduct(
    val categoryId: Int,
    val imagePath: String? = null,
    val imageUrl: String? = null,
    val name: String,
    val description: String,
    val id: Int
)

data class ProductLogsUsers(
    val email: String,
    val id: Int,
    val name: String,
    val role: String,
)