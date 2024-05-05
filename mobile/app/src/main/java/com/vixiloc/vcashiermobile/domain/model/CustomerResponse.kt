package com.vixiloc.vcashiermobile.domain.model

import com.google.gson.annotations.SerializedName

data class CustomerResponse(
    val data: List<CustomerResponseItem> = emptyList()
)

data class CustomerResponseItem(
    val updatedAt: String? = null,
    val name: String,
    val createdAt: String? = null,
    val phoneNumber: String? = null,
    val id: Int? = null
)