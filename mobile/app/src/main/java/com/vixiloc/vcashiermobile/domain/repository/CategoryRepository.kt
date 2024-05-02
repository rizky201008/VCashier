package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.CategoriesResponseDto

interface CategoryRepository {

    suspend fun getCategories(token: String): CategoriesResponseDto
}