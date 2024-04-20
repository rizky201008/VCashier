package com.vixiloc.vcashiermobile.di

import com.vixiloc.vcashiermobile.data.local.UserPreference
import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.repository.AuthRepositoryImpl
import com.vixiloc.vcashiermobile.data.repository.DataStoreRepositoryImpl
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository
import com.vixiloc.vcashiermobile.domain.repository.DataStoreRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { provideAuthRepository(get()) }
    single { provideDataStoreRepository(get()) }
}

fun provideAuthRepository(apiService: ApiService): AuthRepository {
    return AuthRepositoryImpl(apiService)
}

fun provideDataStoreRepository(userPreference: UserPreference): DataStoreRepository {
    return DataStoreRepositoryImpl(userPreference)
}