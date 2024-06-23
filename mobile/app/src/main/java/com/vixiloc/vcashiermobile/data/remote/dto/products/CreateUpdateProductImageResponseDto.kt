package com.vixiloc.vcashiermobile.data.remote.dto.products

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage

data class CreateUpdateProductImageResponseDto(
    @SerializedName("message")
    val message: String,
)

fun CreateUpdateProductImageResponseDto.toDomain(): OnlyResponseMessage = OnlyResponseMessage(
    message = message
)