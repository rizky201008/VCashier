package com.vixiloc.vcashiermobile.data.remote.dto.users


import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.user.UsersResponse
import com.vixiloc.vcashiermobile.domain.model.user.UsersResponseData

data class UsersResponseDto(
    @SerializedName("data")
    val data: List<UsersResponseDataDto>
)

data class UsersResponseDataDto(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

fun UsersResponseDataDto.toDomain() =
    UsersResponseData(id = id, name = name, role = role, email = email)

fun UsersResponseDto.toDomain() = UsersResponse(data.map { it.toDomain() })