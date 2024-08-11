package com.vixiloc.vcashiermobile.domain.model.user

import com.vixiloc.vcashiermobile.data.remote.dto.users.UpdateUserRequestDto

data class UpdateUserRequest(val id: Int, val role: String)

fun UpdateUserRequest.toDto() = UpdateUserRequestDto(
    id = id,
    role = role
)