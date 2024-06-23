package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginResponseDto

interface AuthRepository {
    suspend fun login(data: LoginRequestDto): LoginResponseDto
}