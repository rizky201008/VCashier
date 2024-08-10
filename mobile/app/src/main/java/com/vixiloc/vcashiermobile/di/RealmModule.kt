package com.vixiloc.vcashiermobile.di

import com.vixiloc.vcashiermobile.data.local.realm.CartItemsDao
import com.vixiloc.vcashiermobile.data.local.realm.CartItemsDaoImpl
import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val realmModule = module {
    single { provideRealm() }
    single<CartItemsDao> { CartItemsDaoImpl(get()) }
}

fun provideRealm(): Realm {
    val config = RealmConfiguration.Builder(
        schema = setOf(
            CartItems::class
        )
    )
        .deleteRealmIfMigrationNeeded()
        .compactOnLaunch()
        .build()

    return Realm.open(config)
}