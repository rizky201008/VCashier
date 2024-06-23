package com.vixiloc.vcashiermobile.data.remote.dto.categories

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage

data class CreateUpdateCategoryResponseDto(

    @field:SerializedName("message")
    val message: String
)

fun CreateUpdateCategoryResponseDto.toOnlyResponseMessage(): OnlyResponseMessage = OnlyResponseMessage(message = message)