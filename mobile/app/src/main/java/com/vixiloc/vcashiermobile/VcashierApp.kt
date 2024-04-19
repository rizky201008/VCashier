package com.vixiloc.vcashiermobile

import android.app.Application
import com.vixiloc.vcashiermobile.di.*
import org.koin.core.context.startKoin

class VcashierApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(retrofitModule, viewModelModule, repositoryModule, useCaseModule))
        }
    }
}