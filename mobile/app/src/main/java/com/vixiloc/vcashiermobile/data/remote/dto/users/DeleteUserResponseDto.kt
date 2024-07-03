package com.vixiloc.vcashiermobile.data.remote.dto.users

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.user.DeleteUserResponse

data class DeleteUserResponseDto(
    @SerializedName("message")
    val message: String
)

fun DeleteUserResponseDto.toDomain() = DeleteUserResponse(message = message)