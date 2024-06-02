package com.vixiloc.vcashiermobile.di

import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import org.koin.dsl.module

val useCaseModule = module {
    factory<UseCaseManager> {
        UseCaseManager(
            transactionRepository = get(),
            categoryRepository = get(),
            customerRepository = get(),
            dataStoreRepository = get(),
            productsRepository = get(),
            authRepository = get(),
            paymentsRepository = get()
        )
    }
}