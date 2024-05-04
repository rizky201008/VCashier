package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.CustomerResponseDto

interface CustomerRepository {

    suspend fun getCustomers(token: String): CustomerResponseDto
}