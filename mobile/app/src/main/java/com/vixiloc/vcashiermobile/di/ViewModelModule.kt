package com.vixiloc.vcashiermobile.di

import com.vixiloc.vcashiermobile.domain.use_case.Login
import com.vixiloc.vcashiermobile.presentation.screens.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { provideLoginViewModel(get()) }
}
fun provideLoginViewModel(login: Login): LoginViewModel {
    return LoginViewModel(login = login)
}