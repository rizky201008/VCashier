package com.vixiloc.vcashiermobile.di

import com.vixiloc.vcashiermobile.data.remote.ApiService
import com.vixiloc.vcashiermobile.data.repository.AuthRepositoryImpl
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { provideAuthRepository(get()) }
}

fun provideAuthRepository(apiService: ApiService): AuthRepository {
    return AuthRepositoryImpl(apiService)
}