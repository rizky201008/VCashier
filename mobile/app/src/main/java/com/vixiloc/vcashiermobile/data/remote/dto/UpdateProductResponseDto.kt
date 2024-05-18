package com.vixiloc.vcashiermobile.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage

data class UpdateProductResponseDto(
    @SerializedName("message")
    val message: String
)

fun UpdateProductResponseDto.toDomain(): OnlyResponseMessage {
    return OnlyResponseMessage(
        message = message
    )
}
