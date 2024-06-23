package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.categories.CategoriesResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.categories.CreateUpdateCategoryRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.categories.CreateUpdateCategoryResponseDto

interface CategoryRepository {

    suspend fun getCategories(token: String): CategoriesResponseDto

    suspend fun createCategory(
        token: String,
        data: CreateUpdateCategoryRequestDto
    ): CreateUpdateCategoryResponseDto

    suspend fun updateCategory(
        token: String,
        data: CreateUpdateCategoryRequestDto
    ): CreateUpdateCategoryResponseDto

    suspend fun deleteCategory(
        token: String,
        categoryId: String
    ): CreateUpdateCategoryResponseDto
}