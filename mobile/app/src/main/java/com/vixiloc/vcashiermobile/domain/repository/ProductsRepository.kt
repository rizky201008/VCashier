package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.CreateProductResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateUpdateImageResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateUpdateProductRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.ProductsResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProductsRepository {

    suspend fun getProducts(token: String): ProductsResponseDto

    suspend fun createProduct(
        token: String,
        data: CreateUpdateProductRequestDto
    ): CreateProductResponseDto

    suspend fun addImage(
        token: String,
        productId: RequestBody,
        image: MultipartBody.Part
    ): CreateUpdateImageResponseDto

    suspend fun updateImage(
        token: String,
        data: RequestBody
    ): CreateUpdateImageResponseDto

}