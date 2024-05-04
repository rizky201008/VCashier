package com.vixiloc.vcashiermobile.di

import com.vixiloc.vcashiermobile.data.local.prefs.UserPreference
import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.repository.AuthRepositoryImpl
import com.vixiloc.vcashiermobile.data.repository.CategoryRepositoryImpl
import com.vixiloc.vcashiermobile.data.repository.CustomerRepositoryImpl
import com.vixiloc.vcashiermobile.data.repository.DataStoreRepositoryImpl
import com.vixiloc.vcashiermobile.data.repository.ProductsRepositoryImpl
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository
import com.vixiloc.vcashiermobile.domain.repository.CategoryRepository
import com.vixiloc.vcashiermobile.domain.repository.CustomerRepository
import com.vixiloc.vcashiermobile.domain.repository.DataStoreRepository
import com.vixiloc.vcashiermobile.domain.repository.ProductsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { provideAuthRepository(get()) }
    single { provideDataStoreRepository(get()) }
    single { provideProductsRepository(get()) }
    single { provideCategoryRepository(get()) }
    single { provideCustomerRepository(get()) }
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

fun provideCategoryRepository(apiService: ApiService): CategoryRepository {
    return CategoryRepositoryImpl(apiService)
}

fun provideCustomerRepository(apiService: ApiService): CustomerRepository {
    return CustomerRepositoryImpl(apiService)
}