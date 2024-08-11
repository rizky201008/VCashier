package com.vixiloc.vcashiermobile.data.remote.dto.users

import com.google.gson.annotations.SerializedName

data class UpdateUserRequestDto(
    @SerializedName("id") val id: Int,
    @SerializedName("role") val role: String
)