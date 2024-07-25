package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.users.GetRoleResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.users.UsersResponseDto

interface UserRepository {
    suspend fun getUsers(token: String): UsersResponseDto
    suspend fun getRole(token: String): GetRoleResponseDto
}