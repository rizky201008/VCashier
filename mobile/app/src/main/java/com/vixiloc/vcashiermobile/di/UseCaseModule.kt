package com.vixiloc.vcashiermobile.di

import com.vixiloc.vcashiermobile.commons.HttpHandler
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository
import com.vixiloc.vcashiermobile.domain.repository.CategoryRepository
import com.vixiloc.vcashiermobile.domain.repository.CustomerRepository
import com.vixiloc.vcashiermobile.domain.repository.DataStoreRepository
import com.vixiloc.vcashiermobile.domain.repository.ProductsRepository
import com.vixiloc.vcashiermobile.domain.repository.TransactionRepository
import com.vixiloc.vcashiermobile.domain.use_case.CreateCategory
import com.vixiloc.vcashiermobile.domain.use_case.CreateCustomer
import com.vixiloc.vcashiermobile.domain.use_case.CreateImage
import com.vixiloc.vcashiermobile.domain.use_case.CreateProduct
import com.vixiloc.vcashiermobile.domain.use_case.CreateTransaction
import com.vixiloc.vcashiermobile.domain.use_case.DeleteCategory
import com.vixiloc.vcashiermobile.domain.use_case.DeleteCustomer
import com.vixiloc.vcashiermobile.domain.use_case.GetCategories
import com.vixiloc.vcashiermobile.domain.use_case.GetCustomers
import com.vixiloc.vcashiermobile.domain.use_case.GetProduct
import com.vixiloc.vcashiermobile.domain.use_case.GetProducts
import com.vixiloc.vcashiermobile.domain.use_case.GetToken
import com.vixiloc.vcashiermobile.domain.use_case.Login
import com.vixiloc.vcashiermobile.domain.use_case.SaveToken
import com.vixiloc.vcashiermobile.domain.use_case.UpdateCategory
import com.vixiloc.vcashiermobile.domain.use_case.UpdateCustomer
import com.vixiloc.vcashiermobile.domain.use_case.UpdateImage
import com.vixiloc.vcashiermobile.domain.use_case.UpdateProduct
import org.koin.dsl.module

val useCaseModule = module {
    single { provideHttpHandler() }
    single { provideLoginUseCase(get(), get(), get()) }
    single { provideGetTokenUseCase(get()) }
    single { provideSaveTokenUseCase(get()) }
    single { provideGetProductsUseCase(get(), get(), get()) }
    single { provideGetCategoriesUseCase(get(), get(), get()) }
    single { provideCreateCategoryUseCase(get(), get(), get()) }
    single { provideUpdateCategoryUseCase(get(), get(), get()) }
    single { provideDeleteCategoryUseCase(get(), get(), get()) }
    single { provideGetCustomersUseCase(get(), get(), get()) }
    single { provideCreateCustomerUseCase(get(), get(), get()) }
    single { provideUpdateCustomerUseCase(get(), get(), get()) }
    single { provideDeleteCustomerUseCase(get(), get(), get()) }
    single { provideCreateProductUseCase(get(), get(), get()) }
    single { provideCreateImageUseCase(get(), get(), get()) }
    single { provideUpdateImageUseCase(get(), get(), get()) }
    single { provideUpdateProductUseCase(get(), get(), get()) }
    single { provideGetProductUseCase(get(), get(), get()) }
    single { provideCreateTransactionUseCase(get(), get(), get()) }
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

fun provideGetProductsUseCase(
    getToken: GetToken,
    repository: ProductsRepository,
    httpHandler: HttpHandler
): GetProducts {
    return GetProducts(getToken = getToken, repository = repository, httpHandler = httpHandler)
}

fun provideGetCategoriesUseCase(
    repository: CategoryRepository,
    getToken: GetToken,
    httpHandler: HttpHandler
): GetCategories {
    return GetCategories(repository, getToken = getToken, httpHandler = httpHandler)
}

fun provideCreateCategoryUseCase(
    repository: CategoryRepository,
    getToken: GetToken, httpHandler: HttpHandler
): CreateCategory {
    return CreateCategory(repository, getToken = getToken, httpHandler = httpHandler)
}

fun provideUpdateCategoryUseCase(
    repository: CategoryRepository,
    getToken: GetToken, httpHandler: HttpHandler
): UpdateCategory {
    return UpdateCategory(repository, getToken = getToken, httpHandler = httpHandler)
}

fun provideDeleteCategoryUseCase(
    repository: CategoryRepository,
    getToken: GetToken, httpHandler: HttpHandler
): DeleteCategory {
    return DeleteCategory(repository, getToken = getToken, httpHandler = httpHandler)
}

fun provideGetCustomersUseCase(
    repository: CustomerRepository,
    getToken: GetToken,
    httpHandler: HttpHandler
): GetCustomers {
    return GetCustomers(repository, getToken = getToken, httpHandler = httpHandler)
}

fun provideCreateCustomerUseCase(
    repository: CustomerRepository,
    getToken: GetToken,
    httpHandler: HttpHandler
): CreateCustomer {
    return CreateCustomer(repository, getToken = getToken, httpHandler = httpHandler)
}

fun provideUpdateCustomerUseCase(
    repository: CustomerRepository,
    getToken: GetToken,
    httpHandler: HttpHandler
): UpdateCustomer {
    return UpdateCustomer(repository, getToken = getToken, httpHandler = httpHandler)
}

fun provideDeleteCustomerUseCase(
    repository: CustomerRepository,
    getToken: GetToken,
    httpHandler: HttpHandler
): DeleteCustomer {
    return DeleteCustomer(repository, getToken = getToken, httpHandler = httpHandler)
}

fun provideCreateProductUseCase(
    repository: ProductsRepository,
    getToken: GetToken,
    httpHandler: HttpHandler
): CreateProduct {
    return CreateProduct(repository, getToken = getToken, httpHandler = httpHandler)
}

fun provideCreateImageUseCase(
    repository: ProductsRepository,
    getToken: GetToken,
    httpHandler: HttpHandler
): CreateImage {
    return CreateImage(repository, getToken = getToken, httpHandler = httpHandler)
}

fun provideUpdateImageUseCase(
    repository: ProductsRepository,
    getToken: GetToken,
    httpHandler: HttpHandler
): UpdateImage {
    return UpdateImage(repository, getToken = getToken, httpHandler = httpHandler)
}

fun provideUpdateProductUseCase(
    repository: ProductsRepository,
    getToken: GetToken,
    httpHandler: HttpHandler
): UpdateProduct {
    return UpdateProduct(repository, getToken = getToken, httpHandler = httpHandler)
}

fun provideGetProductUseCase(
    getToken: GetToken,
    repository: ProductsRepository,
    httpHandler: HttpHandler
): GetProduct {
    return GetProduct(getToken = getToken, repository = repository, httpHandler = httpHandler)
}

fun provideCreateTransactionUseCase(
    repository: TransactionRepository,
    getToken: GetToken,
    httpHandler: HttpHandler
): CreateTransaction {
    return CreateTransaction(repository, getToken = getToken, httpHandler = httpHandler)
}