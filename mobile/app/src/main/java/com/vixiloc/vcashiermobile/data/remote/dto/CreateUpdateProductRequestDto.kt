package com.vixiloc.vcashiermobile.data.remote.dto


import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

data class CreateUpdateProductRequestDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("variations")
    val variations: List<VariationDto>
)

data class VariationDto(
    @SerializedName("price")
    val price: Int,
    @SerializedName("price_grocery")
    val priceGrocery: Int,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("unit")
    val unit: String
)