package com.vixiloc.vcashiermobile.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
    @SerializedName("email")
    val email:String,
    @SerializedName("password")
    val password:String
)
