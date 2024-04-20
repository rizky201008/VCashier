package com.vixiloc.vcashiermobile.di

import com.vixiloc.vcashiermobile.domain.use_case.GetToken
import com.vixiloc.vcashiermobile.domain.use_case.Login
import com.vixiloc.vcashiermobile.domain.use_case.SaveToken
import com.vixiloc.vcashiermobile.presentation.screens.login.LoginViewModel
import com.vixiloc.vcashiermobile.presentation.screens.welcome.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { provideLoginViewModel(get(), get()) }
    viewModel { provideWelcomeViewModel(get()) }
}

fun provideLoginViewModel(login: Login, saveToken: SaveToken): LoginViewModel {
    return LoginViewModel(login = login, saveToken = saveToken)
}

fun provideWelcomeViewModel(getToken: GetToken): WelcomeViewModel {
    return WelcomeViewModel(getToken = getToken)
}