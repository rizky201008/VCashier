package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.CreateUpdateCustomerRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateUpdateCustomerResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.CustomerResponseDto
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

    override suspend fun deleteCustomer(
        token: String,
        customerId: String
    ): CreateUpdateCustomerResponseDto {
        return api.deleteCustomer("Bearer $token", customerId)
    }
}