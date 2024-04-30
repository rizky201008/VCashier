package com.vixiloc.vcashiermobile.di

import com.vixiloc.vcashiermobile.data.local.prefs.UserPreference
import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.repository.AuthRepositoryImpl
import com.vixiloc.vcashiermobile.data.repository.DataStoreRepositoryImpl
import com.vixiloc.vcashiermobile.data.repository.ProductsRepositoryImpl
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository
import com.vixiloc.vcashiermobile.domain.repository.DataStoreRepository
import com.vixiloc.vcashiermobile.domain.repository.ProductsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { provideAuthRepository(get()) }
    single { provideDataStoreRepository(get()) }
    single { provideProductsRepository(get()) }
}

fun provideAuthRepository(apiService: ApiService): AuthRepository {
    return AuthRepositoryImpl(apiService)
}

fun provideDataStoreRepository(userPreference: UserPreference): DataStoreRepository {
    return DataStoreRepositoryImpl(userPreference)
}

fun provideProductsRepository(apiService: ApiService): ProductsRepository {
    return ProductsRepositoryImpl(apiService)
}