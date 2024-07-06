package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.users.DeleteUserRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.users.DeleteUserResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.users.UsersResponseDto
import com.vixiloc.vcashiermobile.domain.repository.UserRepository

class UserRepositoryImpl(private val apiService: ApiService) : UserRepository {
    override suspend fun getUsers(token: String): UsersResponseDto {
        return apiService.getUsers("Bearer $token")
    }

    override suspend fun deleteUser(
        token: String,
        data: DeleteUserRequestDto
    ): DeleteUserResponseDto {
        return apiService.deleteUser("Bearer $token", data = data.id.toString())
    }
}