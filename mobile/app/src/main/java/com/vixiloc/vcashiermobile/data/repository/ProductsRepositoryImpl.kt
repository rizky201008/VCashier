package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.CreateProductResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateUpdateImageResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateUpdateProductRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.ProductsResponseDto
import com.vixiloc.vcashiermobile.domain.repository.ProductsRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProductsRepositoryImpl(private val apiService: ApiService) : ProductsRepository {
    override suspend fun getProducts(token: String): ProductsResponseDto {
        return apiService.getProducts("Bearer $token")
    }

    override suspend fun createProduct(
        token: String,
        data: CreateUpdateProductRequestDto
    ): CreateProductResponseDto {
        return apiService.createProduct("Bearer $token", data = data)
    }

    override suspend fun addImage(
        token: String,
        productId: RequestBody,
        image: MultipartBody.Part
    ): CreateUpdateImageResponseDto {
        return apiService.addImage("Bearer $token", productId, image)
    }

    override suspend fun updateImage(
        token: String,
        data: RequestBody
    ): CreateUpdateImageResponseDto {
        return apiService.updateImage("Bearer $token", data = data)
    }
}