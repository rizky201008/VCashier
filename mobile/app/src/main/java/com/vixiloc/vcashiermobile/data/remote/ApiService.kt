package com.vixiloc.vcashiermobile.data.remote

import com.vixiloc.vcashiermobile.data.remote.Routes.LOGIN
import com.vixiloc.vcashiermobile.data.remote.Routes.PRODUCTS
import com.vixiloc.vcashiermobile.data.remote.dto.LoginRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.LoginResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.ProductsResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST(LOGIN)
    suspend fun login(@Body data: LoginRequestDto): LoginResponseDto

    @GET(PRODUCTS)
    suspend fun getProducts(
        @Header("Authorization") token: String,
    ): ProductsResponseDto

}