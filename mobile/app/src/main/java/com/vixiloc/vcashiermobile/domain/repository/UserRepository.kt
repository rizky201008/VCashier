package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.users.UsersResponseDto

interface UserRepository {
    suspend fun getUsers(token: String): UsersResponseDto
}