package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.users.DeleteUserRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.users.DeleteUserResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.users.UsersResponseDto

interface UserRepository {
    suspend fun getUsers(token: String): UsersResponseDto
    suspend fun deleteUser(token: String, data: DeleteUserRequestDto): DeleteUserResponseDto
}