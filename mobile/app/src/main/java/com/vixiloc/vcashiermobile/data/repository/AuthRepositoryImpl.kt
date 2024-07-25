package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LogoutResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.RegisterRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.RegisterResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.ResetPasswordResponseDto
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository

class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository {
    override suspend fun login(data: LoginRequestDto): LoginResponseDto {
        return apiService.login(data)
    }

    override suspend fun register(data: RegisterRequestDto): RegisterResponseDto {
        return apiService.register(data)
    }

    override suspend fun resetPassword(token: String, id: String): ResetPasswordResponseDto {
        return apiService.resetPassword("Bearer $token", id)
    }

    override suspend fun logout(token :String): LogoutResponseDto {
        return apiService.logout(token = "Bearer $token")
    }
}