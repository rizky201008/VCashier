package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.CustomerResponseDto
import com.vixiloc.vcashiermobile.domain.repository.CustomerRepository

class CustomerRepositoryImpl(private val api: ApiService) : CustomerRepository {
    override suspend fun getCustomers(token: String): CustomerResponseDto {
        return api.getCustomers("Bearer $token")
    }
}