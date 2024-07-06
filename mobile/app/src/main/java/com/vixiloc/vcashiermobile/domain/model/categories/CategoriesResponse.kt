package com.vixiloc.vcashiermobile.domain.model.categories

import com.vixiloc.vcashiermobile.domain.model.products.Category

data class CategoriesResponse(
    val categoriesResponseDto: List<CategoriesResponseItem>
)

data class CategoriesResponseItem(
    val name: String,
    val id: Int
)

fun CategoriesResponseItem.toCategory() = Category(
    name = name,
    id = id
)