package com.vixiloc.vcashiermobile.data.remote.dto.customers

import com.google.gson.annotations.SerializedName

data class CreateUpdateCustomerRequestDto(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("phone_number")
    val phoneNumber: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)
