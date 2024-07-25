package com.vixiloc.vcashiermobile.data.remote.dto.users

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.user.GetRoleResponse

data class GetRoleResponseDto(
    @SerializedName("role")
    val role: String
)

fun GetRoleResponseDto.toDomain() = GetRoleResponse(
    role = role
)