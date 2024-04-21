package com.vixiloc.vcashiermobile.domain.repository

import com.vixiloc.vcashiermobile.data.remote.dto.ProductsResponseDto

interface ProductsRepository {

    suspend fun getProducts(token: String): ProductsResponseDto
}