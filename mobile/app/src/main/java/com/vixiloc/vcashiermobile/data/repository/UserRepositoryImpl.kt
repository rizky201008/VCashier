package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.users.GetRoleResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.users.UpdateUserRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.users.UpdateUserResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.users.UsersResponseDto
import com.vixiloc.vcashiermobile.domain.repository.UserRepository

class UserRepositoryImpl(private val apiService: ApiService) : UserRepository {
    override suspend fun getUsers(token: String): UsersResponseDto {
        return apiService.getUsers("Bearer $token")
    }

    override suspend fun getRole(token: String): GetRoleResponseDto {
        return apiService.getRole("Bearer $token")
    }

    override suspend fun updateUser(
        data: UpdateUserRequestDto,
        token: String
    ): UpdateUserResponseDto {
        return apiService.updateUser(data = data, token = "Bearer $token")
    }
}