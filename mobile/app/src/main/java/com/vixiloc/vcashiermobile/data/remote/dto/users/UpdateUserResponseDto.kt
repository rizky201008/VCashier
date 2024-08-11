package com.vixiloc.vcashiermobile.data.remote.dto.users

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.user.UpdateUserResponse

data class UpdateUserResponseDto(
    @SerializedName("message")
    val message: String
)

fun UpdateUserResponseDto.toDomain(): UpdateUserResponse {
    return UpdateUserResponse(
        message = message
    )
}
