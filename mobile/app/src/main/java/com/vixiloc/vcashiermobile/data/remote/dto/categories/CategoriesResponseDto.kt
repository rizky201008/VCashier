package com.vixiloc.vcashiermobile.data.remote.dto.categories

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.CategoriesResponse
import com.vixiloc.vcashiermobile.domain.model.CategoriesResponseItem

data class CategoriesResponseDto(

    @field:SerializedName("data")
    val categoriesResponseDto: List<CategoriesResponseDtoItem>
)

data class CategoriesResponseDtoItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)

fun CategoriesResponseDtoItem.toCategoriesResponseItem() = CategoriesResponseItem(
    name = name,
    id = id
)

fun CategoriesResponseDto.toCategoriesResponse() = CategoriesResponse(
    categoriesResponseDto = categoriesResponseDto.map { it.toCategoriesResponseItem() }
)