package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRegisterResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.RegisterRequestDto

interface AuthRepository {
    suspend fun login(data: LoginRequestDto): LoginRegisterResponseDto
    suspend fun register(data: RegisterRequestDto): LoginRegisterResponseDto
}