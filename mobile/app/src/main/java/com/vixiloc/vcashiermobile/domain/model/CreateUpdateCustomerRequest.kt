package com.vixiloc.vcashiermobile.domain.model

import com.vixiloc.vcashiermobile.data.remote.dto.CreateUpdateCustomerRequestDto

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
