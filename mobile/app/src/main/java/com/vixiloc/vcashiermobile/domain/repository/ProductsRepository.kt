package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.product_logs.CreateProductLogResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.product_logs.CreateProductLogsRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.product_logs.ProductLogsResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.CreateProductResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.CreateUpdateProductImageResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.CreateProductRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.ProductResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.ProductsResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.UpdateProductResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProductsRepository {

    suspend fun getProducts(token: String): ProductsResponseDto

    suspend fun createProduct(
        token: String,
        data: CreateProductRequestDto
    ): CreateProductResponseDto

    suspend fun addImage(
        token: String,
        productId: RequestBody,
        image: MultipartBody.Part
    ): CreateUpdateProductImageResponseDto

    suspend fun updateImage(
        token: String,
        productId: RequestBody,
        newImage: MultipartBody.Part
    ): CreateUpdateProductImageResponseDto

    suspend fun updateProduct(
        token: String,
        data: CreateProductRequestDto
    ): UpdateProductResponseDto

    suspend fun getProduct(
        token: String,
        id: String
    ): ProductResponseDto

    suspend fun getProductLogs(token: String): ProductLogsResponseDto

    suspend fun createProductLogs(
        token: String,
        data: CreateProductLogsRequestDto
    ): CreateProductLogResponseDto
}