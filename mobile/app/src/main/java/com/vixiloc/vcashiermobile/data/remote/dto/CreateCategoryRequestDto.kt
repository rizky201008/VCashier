package com.vixiloc.vcashiermobile.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateCategoryRequestDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("id")
    val id: Int? = null
)
