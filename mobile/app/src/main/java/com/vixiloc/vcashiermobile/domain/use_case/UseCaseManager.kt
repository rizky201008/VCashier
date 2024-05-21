package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.commons.HttpHandler
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository
import com.vixiloc.vcashiermobile.domain.repository.CategoryRepository
import com.vixiloc.vcashiermobile.domain.repository.CustomerRepository
import com.vixiloc.vcashiermobile.domain.repository.DataStoreRepository
import com.vixiloc.vcashiermobile.domain.repository.ProductsRepository
import com.vixiloc.vcashiermobile.domain.repository.TransactionRepository

class UseCaseManager(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val customerRepository: CustomerRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val productsRepository: ProductsRepository,
    private val authRepository: AuthRepository,
) {
    fun httpHandler() = HttpHandler()
    fun saveToken() = SaveToken(repository = dataStoreRepository)
    fun loginUseCase() =
        Login(repository = authRepository, httpHandler = httpHandler(), saveToken = saveToken())

    fun getTokenUseCase() = GetToken(repository = dataStoreRepository)
    fun getProductsUseCase() = GetProducts(
        repository = productsRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )

    fun getCategoriesUseCase() = GetCategories(
        repository = categoryRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )
    fun createCategoryUseCase() = CreateCategory(
        repository = categoryRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )
    fun updateCategoryUseCase() = UpdateCategory(
        repository = categoryRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )
    fun deleteCategoryUseCase() = DeleteCategory(
        repository = categoryRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )
    fun getCustomersUseCase() = GetCustomers(
        repository = customerRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )
    fun createCustomerUseCase() = CreateCustomer(
        repository = customerRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )
    fun updateCustomerUseCase() = UpdateCustomer(
        repository = customerRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )
    fun deleteCustomerUseCase() = DeleteCustomer(
        repository = customerRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )
    fun createProductUseCase() = CreateProduct(
        repository = productsRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )
    fun updateProductUseCase() = UpdateProduct(
        repository = productsRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )
    fun createImageUseCase() = CreateImage(
        repository = productsRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )
    fun updateImageUseCase() = UpdateImage(
        repository = productsRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )
    fun getProductUseCase() = GetProduct(
        repository = productsRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )
    fun createTransactionUseCase() = CreateTransaction(
        repository = transactionRepository,
        getToken = getTokenUseCase(),
        httpHandler = httpHandler()
    )
}