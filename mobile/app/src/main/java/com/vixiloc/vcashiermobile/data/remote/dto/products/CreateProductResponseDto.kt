package com.vixiloc.vcashiermobile.data.remote.dto.products


import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.products.CreateProductResponse

data class CreateProductResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("message")
    val message: String
)

fun CreateProductResponseDto.toDomain() = CreateProductResponse(
    message = message,
    id = id
)