package com.vixiloc.vcashiermobile.di

import android.content.Context
import com.vixiloc.vcashiermobile.data.local.UserPreference
import com.vixiloc.vcashiermobile.data.local.dataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { provideUserPreference(androidContext()) }
}

fun provideUserPreference(context: Context): UserPreference {

    return UserPreference.getInstance(context.dataStore)
}