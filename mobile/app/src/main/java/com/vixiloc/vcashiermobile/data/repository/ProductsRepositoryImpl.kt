package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.dto.products.CreateProductResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.CreateUpdateProductImageResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.CreateUpdateProductRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.ProductResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.ProductsResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.UpdateProductResponseDto
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
    ): CreateUpdateProductImageResponseDto {
        return apiService.addImage("Bearer $token", productId, image)
    }

    override suspend fun updateProduct(
        token: String,
        data: CreateUpdateProductRequestDto
    ): UpdateProductResponseDto {
        return apiService.updateProduct(token = "Bearer $token", data = data)
    }

    override suspend fun getProduct(token: String, id: String): ProductResponseDto {
        return apiService.getProduct("Bearer $token", id)
    }

    override suspend fun updateImage(
        token: String,
        productId: RequestBody,
        newImage: MultipartBody.Part
    ): CreateUpdateProductImageResponseDto {
        return apiService.updateImage("Bearer $token", productId = productId, newImage = newImage)
    }
}