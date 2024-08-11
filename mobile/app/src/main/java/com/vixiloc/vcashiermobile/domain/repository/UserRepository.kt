package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.users.GetRoleResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.users.UpdateUserRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.users.UpdateUserResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.users.UsersResponseDto

interface UserRepository {
    suspend fun getUsers(token: String): UsersResponseDto
    suspend fun getRole(token: String): GetRoleResponseDto
    suspend fun updateUser(data: UpdateUserRequestDto, token: String): UpdateUserResponseDto
}