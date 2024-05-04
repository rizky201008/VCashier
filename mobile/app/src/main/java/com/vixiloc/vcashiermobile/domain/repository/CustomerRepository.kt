package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.CreateUpdateCustomerRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateUpdateCustomerResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.CustomerResponseDto

interface CustomerRepository {

    suspend fun getCustomers(token: String): CustomerResponseDto

    suspend fun createCustomer(
        token: String,
        data: CreateUpdateCustomerRequestDto
    ): CreateUpdateCustomerResponseDto
}