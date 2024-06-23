package com.vixiloc.vcashiermobile.domain.model

import com.vixiloc.vcashiermobile.data.remote.dto.categories.CreateUpdateCategoryRequestDto

data class CreateUpdateCategoryRequest(
    val name: String,
    val id: Int? = null
)

fun CreateUpdateCategoryRequest.toCreateUpdateCategoryRequestDto() = CreateUpdateCategoryRequestDto(
    name = name,
    id = id
)
