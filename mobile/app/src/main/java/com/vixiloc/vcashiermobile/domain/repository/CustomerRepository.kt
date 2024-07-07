package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.customers.CreateUpdateCustomerRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.customers.CreateUpdateCustomerResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.customers.CustomerResponseDto

interface CustomerRepository {

    suspend fun getCustomers(token: String): CustomerResponseDto

    suspend fun createCustomer(
        token: String,
        data: CreateUpdateCustomerRequestDto
    ): CreateUpdateCustomerResponseDto

    suspend fun updateCustomer(
        token: String,
        data: CreateUpdateCustomerRequestDto
    ): CreateUpdateCustomerResponseDto
}