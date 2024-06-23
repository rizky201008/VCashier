package com.vixiloc.vcashiermobile.data.remote.dto.customers

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage

data class CreateUpdateCustomerResponseDto(

    @field:SerializedName("message")
    val message: String
)

fun CreateUpdateCustomerResponseDto.toOnlyResponseMessage(): OnlyResponseMessage = OnlyResponseMessage(message = message)
