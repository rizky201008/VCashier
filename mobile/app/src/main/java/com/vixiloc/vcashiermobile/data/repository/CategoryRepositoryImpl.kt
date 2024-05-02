package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.CategoriesResponseDto
import com.vixiloc.vcashiermobile.domain.repository.CategoryRepository

class CategoryRepositoryImpl(private val api: ApiService) : CategoryRepository {
    override suspend fun getCategories(token: String): CategoriesResponseDto {
        return api.getCategories(token)
    }
}