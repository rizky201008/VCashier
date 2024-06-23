package com.vixiloc.vcashiermobile.domain.model.customers

import com.vixiloc.vcashiermobile.data.remote.dto.customers.CreateUpdateCustomerRequestDto

data class CreateUpdateCustomerRequest(
    val name: String,
    val id: Int? = null,
    val phoneNumber: String? = null
)

fun CreateUpdateCustomerRequest.toCreateUpdateCustomerRequestDto() = CreateUpdateCustomerRequestDto(
    name = name,
    id = id,
    phoneNumber = phoneNumber
)
