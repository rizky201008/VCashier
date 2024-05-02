package com.vixiloc.vcashiermobile.domain.model

import com.vixiloc.vcashiermobile.data.remote.dto.CreateCategoryRequestDto

data class CreateCategoryRequest(
    val name: String,
    val id: Int? = null
)

fun CreateCategoryRequest.toCreateCategoryRequestDto() = CreateCategoryRequestDto(
    name = name,
    id = id
)
