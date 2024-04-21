package com.vixiloc.vcashiermobile.di

import com.vixiloc.vcashiermobile.commons.HttpHandler
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository
import com.vixiloc.vcashiermobile.domain.repository.DataStoreRepository
import com.vixiloc.vcashiermobile.domain.use_case.GetToken
import com.vixiloc.vcashiermobile.domain.use_case.Login
import com.vixiloc.vcashiermobile.domain.use_case.SaveToken
import org.koin.dsl.module

val useCaseModule = module {
    single { provideHttpHandler() }
    single { provideLoginUseCase(get(), get(), get()) }
    single { provideGetTokenUseCase(get()) }
    single { provideSaveTokenUseCase(get()) }
}

fun provideHttpHandler(): HttpHandler {
    return HttpHandler()
}

fun provideLoginUseCase(
    repository: AuthRepository,
    httpHandler: HttpHandler,
    saveToken: SaveToken
): Login {
    return Login(repository = repository, httpHandler = httpHandler, saveToken = saveToken)
}

fun provideGetTokenUseCase(repository: DataStoreRepository): GetToken {
    return GetToken(repository)
}

fun provideSaveTokenUseCase(repository: DataStoreRepository): SaveToken {
    return SaveToken(repository)
}