package com.vixiloc.vcashiermobile.domain.model.products

import com.vixiloc.vcashiermobile.data.remote.dto.products.UpdateProductRequestDto

data class UpdateProductRequest(
    val id: Int,
    val name: String,
    val description: String,
    val categoryId: Int,
)

fun UpdateProductRequest.toDto() = UpdateProductRequestDto(
    id = id,
    name = name,
    description = description,
    categoryId = categoryId,
)