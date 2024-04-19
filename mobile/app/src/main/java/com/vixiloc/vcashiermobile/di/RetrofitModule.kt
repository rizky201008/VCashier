package com.vixiloc.vcashiermobile.di

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.remote.Routes
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    factory<ApiService> { provideApiService() }
}

fun provideApiService(): ApiService {
    return Retrofit.Builder()
        .baseUrl(Routes.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}