package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.CategoriesResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateCategoryRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateCategoryResponseDto

interface CategoryRepository {

    suspend fun getCategories(token: String): CategoriesResponseDto

    suspend fun createCategory(
        token: String,
        data: CreateCategoryRequestDto
    ): CreateCategoryResponseDto
}