package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.ProductsResponseDto
import com.vixiloc.vcashiermobile.domain.repository.ProductsRepository

class ProductsRepositoryImpl(private val apiService: ApiService) : ProductsRepository {
    override suspend fun getProducts(token: String): ProductsResponseDto {
        return apiService.getProducts("Bearer $token")
    }
}