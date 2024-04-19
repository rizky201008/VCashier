package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.LoginRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.LoginResponseDto

interface AuthRepository {
    suspend fun login(data: LoginRequestDto): LoginResponseDto
}