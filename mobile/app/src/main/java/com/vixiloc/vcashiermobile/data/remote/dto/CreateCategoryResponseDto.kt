package com.vixiloc.vcashiermobile.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage

data class CreateCategoryResponseDto(

    @field:SerializedName("message")
    val message: String
)

fun CreateCategoryResponseDto.toOnlyResponseMessage(): OnlyResponseMessage = OnlyResponseMessage(message = message)