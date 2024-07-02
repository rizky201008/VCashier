package com.vixiloc.vcashiermobile.domain.model.user

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("data")
    val data: List<UsersResponseData>
)

data class UsersResponseData(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("role")
    val role: String
)