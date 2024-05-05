package com.vixiloc.vcashiermobile.di

import com.vixiloc.vcashiermobile.commons.HttpHandler
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository
import com.vixiloc.vcashiermobile.domain.repository.CategoryRepository
import com.vixiloc.vcashiermobile.domain.repository.CustomerRepository
import com.vixiloc.vcashiermobile.domain.repository.DataStoreRepository
import com.vixiloc.vcashiermobile.domain.repository.ProductsRepository
import com.vixiloc.vcashiermobile.domain.use_case.CreateCategory
import com.vixiloc.vcashiermobile.domain.use_case.CreateCustomer
import com.vixiloc.vcashiermobile.domain.use_case.DeleteCategory
import com.vixiloc.vcashiermobile.domain.use_case.GetCategories
import com.vixiloc.vcashiermobile.domain.use_case.GetCustomers
import com.vixiloc.vcashiermobile.domain.use_case.GetProducts
import com.vixiloc.vcashiermobile.domain.use_case.GetToken
import com.vixiloc.vcashiermobile.domain.use_case.Login
import com.vixiloc.vcashiermobile.domain.use_case.SaveToken
import com.vixiloc.vcashiermobile.domain.use_case.UpdateCategory
import org.koin.dsl.module

val useCaseModule = module {
    single { provideHttpHandler() }
    single { provideLoginUseCase(get(), get(), get()) }
    single { provideGetTokenUseCase(get()) }
    single { provideSaveTokenUseCase(get()) }
    single { provideGetProductsUseCase(get(), get()) }
    single { provideGetCategoriesUseCase(get(), get()) }
    single { provideCreateCategoryUseCase(get(), get()) }
    single { provideUpdateCategoryUseCase(get(), get()) }
    single { provideDeleteCategoryUseCase(get(), get()) }
    single { provideGetCustomersUseCase(get(), get()) }
    single { provideCreateCustomerUseCase(get(), get(), get()) }
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

fun provideGetProductsUseCase(getToken: GetToken, repository: ProductsRepository): GetProducts {
    return GetProducts(getToken = getToken, repository = repository)
}

fun provideGetCategoriesUseCase(repository: CategoryRepository, getToken: GetToken): GetCategories {
    return GetCategories(repository, getToken = getToken)
}

fun provideCreateCategoryUseCase(
    repository: CategoryRepository,
    getToken: GetToken
): CreateCategory {
    return CreateCategory(repository, getToken = getToken)
}

fun provideUpdateCategoryUseCase(
    repository: CategoryRepository,
    getToken: GetToken
): UpdateCategory {
    return UpdateCategory(repository, getToken = getToken)
}

fun provideDeleteCategoryUseCase(
    repository: CategoryRepository,
    getToken: GetToken
): DeleteCategory {
    return DeleteCategory(repository, getToken = getToken)
}

fun provideGetCustomersUseCase(repository: CustomerRepository, getToken: GetToken): GetCustomers {
    return GetCustomers(repository, getToken = getToken)
}

fun provideCreateCustomerUseCase(
    repository: CustomerRepository,
    getToken: GetToken,
    httpHandler: HttpHandler
): CreateCustomer {
    return CreateCustomer(repository, getToken = getToken, httpHandler = httpHandler)
}