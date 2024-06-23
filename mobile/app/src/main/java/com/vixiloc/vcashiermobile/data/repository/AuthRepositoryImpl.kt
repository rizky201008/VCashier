package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginResponseDto
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository

class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository {
    override suspend fun login(data: LoginRequestDto): LoginResponseDto {
        return apiService.login(data)
    }
}