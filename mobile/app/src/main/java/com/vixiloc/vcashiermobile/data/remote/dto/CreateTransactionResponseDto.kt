package com.vixiloc.vcashiermobile.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CreateTransactionResponseDto(
    @SerializedName("message")
    val message: String
)

fun CreateTransactionResponseDto.toDomain() = com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage(
    message = message
)