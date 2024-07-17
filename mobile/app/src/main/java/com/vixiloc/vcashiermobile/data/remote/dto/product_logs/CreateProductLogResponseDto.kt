package com.vixiloc.vcashiermobile.data.remote.dto.product_logs

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.product_logs.CreateProductLogResponse

data class CreateProductLogResponseDto(
    @SerializedName("message")
    val message: String
)

fun CreateProductLogResponseDto.toDomain() = CreateProductLogResponse(message = this.message)
