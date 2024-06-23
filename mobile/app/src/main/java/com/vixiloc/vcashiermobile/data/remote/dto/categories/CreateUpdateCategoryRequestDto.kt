package com.vixiloc.vcashiermobile.data.remote.dto.categories

import com.google.gson.annotations.SerializedName

data class CreateUpdateCategoryRequestDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("id")
    val id: Int? = null
)
