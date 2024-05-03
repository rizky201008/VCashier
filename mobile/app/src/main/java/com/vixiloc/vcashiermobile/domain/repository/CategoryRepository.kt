package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.CategoriesResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateUpdateCategoryRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateUpdateCategoryResponseDto

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
}