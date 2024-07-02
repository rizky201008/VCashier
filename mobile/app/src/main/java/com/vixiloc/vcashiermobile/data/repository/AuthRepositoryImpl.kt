package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRegisterResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.RegisterRequestDto
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository

class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository {
    override suspend fun login(data: LoginRequestDto): LoginRegisterResponseDto {
        return apiService.login(data)
    }

    override suspend fun register(data: RegisterRequestDto): LoginRegisterResponseDto {
        return apiService.register(data)
    }
}