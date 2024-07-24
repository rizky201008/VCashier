package com.vixiloc.vcashiermobile.data.remote.dto.products

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage
import com.vixiloc.vcashiermobile.domain.model.products.UpdateProductResponse

data class UpdateProductResponseDto(
    @SerializedName("message")
    val message: String
)

fun UpdateProductResponseDto.toDomain(): UpdateProductResponse = UpdateProductResponse(
    message = message
)
