package com.vixiloc.vcashiermobile.domain.model.products

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ProductsResponse(
    val data: List<ProductResponseItems?>? = null
) : Parcelable

@Serializable
@Parcelize
data class Category(
    val name: String,
    val id: Int
) : Parcelable

@Serializable
@Parcelize
data class Product(
    val imageUrl: String? = null,
    val name: String,
    val description: String,
    val id: Int,
    val category: Category
) : Parcelable

@Serializable
@Parcelize
data class ProductResponseItems(
    val priceGrocery: Int? = null,
    val unit: String,
    val product: Product,
    val price: Int,
    val id: Int,
    val stock: Int
) : Parcelable
