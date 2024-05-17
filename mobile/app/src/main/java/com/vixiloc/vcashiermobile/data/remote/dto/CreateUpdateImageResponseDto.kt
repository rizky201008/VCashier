package com.vixiloc.vcashiermobile.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage

data class CreateUpdateImageResponseDto(
    @SerializedName("message")
    val message: String,
)

fun CreateUpdateImageResponseDto.toDomain(): OnlyResponseMessage = OnlyResponseMessage(
    message = message
)