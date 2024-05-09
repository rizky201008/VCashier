package com.vixiloc.vcashiermobile.domain.model

data class ProductsResponse(
    val data: List<ProductResponseItems?>? = null
)

data class Category(
    val name: String,
    val id: Int
)

data class Product(
    val imageUrl: String? = null,
    val name: String,
    val description: String,
    val id: Int,
    val category: Category
)

data class ProductResponseItems(
    val priceGrocery: Int? = null,
    val unit: String,
    val product: Product,
    val price: Int,
    val id: Int,
    val stock: Int
)
