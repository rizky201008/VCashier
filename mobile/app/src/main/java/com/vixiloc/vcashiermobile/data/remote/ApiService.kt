package com.vixiloc.vcashiermobile.data.remote

import com.vixiloc.vcashiermobile.data.remote.Routes.CATEGORIES
import com.vixiloc.vcashiermobile.data.remote.Routes.CATEGORIES_CREATE
import com.vixiloc.vcashiermobile.data.remote.Routes.CATEGORIES_UPDATE
import com.vixiloc.vcashiermobile.data.remote.Routes.CUSTOMERS
import com.vixiloc.vcashiermobile.data.remote.Routes.LOGIN
import com.vixiloc.vcashiermobile.data.remote.Routes.PRODUCTS
import com.vixiloc.vcashiermobile.data.remote.dto.CategoriesResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateUpdateCategoryRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.CreateUpdateCategoryResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.CustomerResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.LoginRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.LoginResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.ProductsResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST(LOGIN)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun login(@Body data: LoginRequestDto): LoginResponseDto

    @GET(PRODUCTS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getProducts(
        @Header("Authorization") token: String,
    ): ProductsResponseDto

    @GET(CATEGORIES)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getCategories(
        @Header("Authorization") token: String,
    ): CategoriesResponseDto

    @POST(CATEGORIES_CREATE)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun createCategory(
        @Header("Authorization") token: String,
        @Body data: CreateUpdateCategoryRequestDto
    ): CreateUpdateCategoryResponseDto

    @PUT(CATEGORIES_UPDATE)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun updateCategory(
        @Header("Authorization") token: String,
        @Body data: CreateUpdateCategoryRequestDto
    ): CreateUpdateCategoryResponseDto

    @DELETE("$CATEGORIES/{id}")
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun deleteCategory(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): CreateUpdateCategoryResponseDto

    @GET(CUSTOMERS)
    suspend fun getCustomers(
        @Header("Authorization") token: String,
    ): CustomerResponseDto

}