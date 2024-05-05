package com.vixiloc.vcashiermobile.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateUpdateCustomerRequestDto(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("phone_number")
    val phoneNumber: String,

    @field:SerializedName("id")
    val id: Int? = null
)