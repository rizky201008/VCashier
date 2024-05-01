package com.vixiloc.vcashiermobile.data.remote

import com.vixiloc.vcashiermobile.data.remote.Routes.LOGIN
import com.vixiloc.vcashiermobile.data.remote.Routes.PRODUCTS
import com.vixiloc.vcashiermobile.data.remote.dto.LoginRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.LoginResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.ProductsResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST(LOGIN)
    @Headers("Content-Type: application/json","Accept: application/json")
    suspend fun login(@Body data: LoginRequestDto): LoginResponseDto

    @GET(PRODUCTS)
    @Headers("Content-Type: application/json","Accept: application/json")
    suspend fun getProducts(
        @Header("Authorization") token: String,
    ): ProductsResponseDto

}