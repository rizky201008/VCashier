package com.vixiloc.vcashiermobile.domain.model.user

import com.vixiloc.vcashiermobile.data.remote.dto.users.DeleteUserRequestDto

data class DeleteUserRequest(
    val id: Int
)

fun DeleteUserRequest.toDto() = DeleteUserRequestDto(id = id)
