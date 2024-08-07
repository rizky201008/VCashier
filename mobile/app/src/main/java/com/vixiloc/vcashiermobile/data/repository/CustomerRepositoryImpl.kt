package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.customers.CreateUpdateCustomerRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.customers.CreateUpdateCustomerResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.customers.CustomerResponseDto
import com.vixiloc.vcashiermobile.domain.repository.CustomerRepository

class CustomerRepositoryImpl(private val api: ApiService) : CustomerRepository {
    override suspend fun getCustomers(token: String): CustomerResponseDto {
        return api.getCustomers("Bearer $token")
    }

    override suspend fun createCustomer(
        token: String,
        data: CreateUpdateCustomerRequestDto
    ): CreateUpdateCustomerResponseDto {
        return api.createCustomer("Bearer $token", data)
    }

    override suspend fun updateCustomer(
        token: String,
        data: CreateUpdateCustomerRequestDto
    ): CreateUpdateCustomerResponseDto {
        return api.updateCustomer("Bearer $token", data)
    }
}