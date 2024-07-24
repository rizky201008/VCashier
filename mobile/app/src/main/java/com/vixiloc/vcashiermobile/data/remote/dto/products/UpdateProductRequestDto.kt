package com.vixiloc.vcashiermobile.data.remote.dto.products

import com.google.gson.annotations.SerializedName

data class UpdateProductRequestDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("category_id")
    val categoryId: Int,
)
