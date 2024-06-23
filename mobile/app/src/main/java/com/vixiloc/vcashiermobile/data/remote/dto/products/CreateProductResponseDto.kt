package com.vixiloc.vcashiermobile.data.remote.dto.products


import com.google.gson.annotations.SerializedName

data class CreateProductResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("message")
    val message: String
)

fun CreateProductResponseDto.toDomain() = com.vixiloc.vcashiermobile.domain.model.CreateProductResponse(
    message = message,
    id = id
)