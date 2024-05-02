package com.vixiloc.vcashiermobile.domain.model

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    val data: List<ProductResponseItems?>? = null
)

data class Category(
    val name: String? = null,
    val id: Int? = null
)

data class Product(
    val categoryId: Int? = null,
    val imagePath: String? = null,
    val imageUrl: String? = null,
    val name: String? = null,
    val description: String? = null,
    val id: Int? = null,
    val category: Category? = null
)

data class ProductResponseItems(
    val priceGrocery: Int? = null,
    val unit: String? = null,
    val product: Product? = null,
    val price: Int? = null,
    val productId: Int? = null,
    val id: Int? = null,
    val stock: Int? = null
)
