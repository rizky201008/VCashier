package com.vixiloc.vcashiermobile.data.remote.dto.customers

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponse
import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem

data class CustomerResponseDto(

    @field:SerializedName("data")
    val data: List<CustomerResponseDtoItem> = emptyList()
)

data class CustomerResponseDtoItem(

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("phone_number")
    val phoneNumber: String? = null,

    @field:SerializedName("id")
    val id: Int
)

fun CustomerResponseDtoItem.toModel() = CustomerResponseItem(
    name = name,
    phoneNumber = phoneNumber,
    id = id
)

fun CustomerResponseDto.toCustomerResponse() = CustomerResponse(
    data = data.map { it.toModel() }
)
