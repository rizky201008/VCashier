package com.vixiloc.vcashiermobile.data.remote.dto.products

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.products.UpdateProductVariationResponse

data class UpdateProductVariationResponseDto(
    @SerializedName("message")
    val message: String,
)

fun UpdateProductVariationResponseDto.toDomain() = UpdateProductVariationResponse(
    message = message,
)