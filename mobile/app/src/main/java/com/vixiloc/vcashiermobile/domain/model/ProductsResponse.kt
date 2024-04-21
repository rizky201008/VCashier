package com.vixiloc.vcashiermobile.domain.model

import com.google.gson.annotations.SerializedName

data class ProductsResponse(

    @field:SerializedName("data")
    val data: List<ProductResponseItems?>? = null
)

data class Category(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

data class Product(

    @field:SerializedName("category_id")
    val categoryId: Int? = null,

    @field:SerializedName("image_path")
    val imagePath: String? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("category")
    val category: Category? = null
)

data class ProductResponseItems(

    @field:SerializedName("price_grocery")
    val priceGrocery: Int? = null,

    @field:SerializedName("unit")
    val unit: String? = null,

    @field:SerializedName("product")
    val product: Product? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("product_id")
    val productId: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("stock")
    val stock: Int? = null
)
