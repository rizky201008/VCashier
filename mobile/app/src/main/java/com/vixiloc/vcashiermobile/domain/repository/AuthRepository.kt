package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRegisterResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.RegisterRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.ResetPasswordResponseDto

interface AuthRepository {
    suspend fun login(data: LoginRequestDto): LoginRegisterResponseDto
    suspend fun register(data: RegisterRequestDto): LoginRegisterResponseDto
    suspend fun resetPassword(token: String, id: String): ResetPasswordResponseDto
}