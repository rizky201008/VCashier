package com.vixiloc.vcashiermobile.domain.model.products

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ProductsResponse(
    val data: List<ProductsResponseItems?>? = null
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
data class ProductsVariation (
    val id: Int,
    val price: Int,
    val priceGrocery: Int,
    val stock: Int,
    val unit: String,
    val productId: Int,
) : Parcelable

@Serializable
@Parcelize
data class ProductsResponseItems(
    val category: Category,
    val description: String,
    val id: Int,
    val imageUrl: String?,
    val name: String,
    val variations: List<ProductsVariation>
) : Parcelable
