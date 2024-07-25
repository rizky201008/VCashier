package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LogoutResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.RegisterRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.RegisterResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.ResetPasswordResponseDto

interface AuthRepository {
    suspend fun login(data: LoginRequestDto): LoginResponseDto
    suspend fun register(data: RegisterRequestDto): RegisterResponseDto
    suspend fun resetPassword(token: String, id: String): ResetPasswordResponseDto
    suspend fun logout(token: String): LogoutResponseDto
}