package com.vixiloc.vcashiermobile.data.remote

import com.vixiloc.vcashiermobile.data.remote.Routes.ADD_IMAGE
import com.vixiloc.vcashiermobile.data.remote.Routes.CATEGORIES
import com.vixiloc.vcashiermobile.data.remote.Routes.CUSTOMERS
import com.vixiloc.vcashiermobile.data.remote.Routes.LOGIN
import com.vixiloc.vcashiermobile.data.remote.Routes.PAYMENT_MAKE
import com.vixiloc.vcashiermobile.data.remote.Routes.PAYMENT_METHODS
import com.vixiloc.vcashiermobile.data.remote.Routes.PRODUCTS
import com.vixiloc.vcashiermobile.data.remote.Routes.REGISTER
import com.vixiloc.vcashiermobile.data.remote.Routes.RESET_PASSWORD
import com.vixiloc.vcashiermobile.data.remote.Routes.TRANSACTIONS
import com.vixiloc.vcashiermobile.data.remote.Routes.UPDATE_IMAGE
import com.vixiloc.vcashiermobile.data.remote.Routes.USERS_LIST
import com.vixiloc.vcashiermobile.data.remote.dto.categories.CategoriesResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.CreateProductResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.categories.CreateUpdateCategoryRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.categories.CreateUpdateCategoryResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.customers.CreateUpdateCustomerRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.customers.CreateUpdateCustomerResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.CreateUpdateProductImageResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.CreateUpdateProductRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.customers.CustomerResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRegisterResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.RegisterRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.ResetPasswordResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.MakePaymentRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.MakePaymentResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.PaymentMethodsDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.ProductResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.ProductsResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.TransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.TransactionsResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.UpdateProductResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.users.UsersResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST(LOGIN)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun login(@Body data: LoginRequestDto): LoginRegisterResponseDto

    @POST(REGISTER)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun register(@Body data: RegisterRequestDto): LoginRegisterResponseDto

    @GET(PRODUCTS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getProducts(
        @Header("Authorization") token: String,
    ): ProductsResponseDto

    @GET("$PRODUCTS/{id}")
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getProduct(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): ProductResponseDto

    @POST(PRODUCTS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun createProduct(
        @Header("Authorization") token: String,
        @Body data: CreateUpdateProductRequestDto
    ): CreateProductResponseDto

    @PUT(PRODUCTS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun updateProduct(
        @Header("Authorization") token: String,
        @Body data: CreateUpdateProductRequestDto
    ): UpdateProductResponseDto

    @Multipart
    @POST(ADD_IMAGE)
    @Headers("Accept: application/json")
    suspend fun addImage(
        @Header("Authorization") token: String,
        @Part("product_id") productId: RequestBody,
        @Part image: MultipartBody.Part,
    ): CreateUpdateProductImageResponseDto

    @Multipart
    @POST(UPDATE_IMAGE)
    @Headers("Accept: application/json")
    suspend fun updateImage(
        @Header("Authorization") token: String,
        @Part("product_id") productId: RequestBody,
        @Part newImage: MultipartBody.Part,
    ): CreateUpdateProductImageResponseDto

    @GET(CATEGORIES)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getCategories(
        @Header("Authorization") token: String,
    ): CategoriesResponseDto

    @POST(CATEGORIES)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun createCategory(
        @Header("Authorization") token: String,
        @Body data: CreateUpdateCategoryRequestDto
    ): CreateUpdateCategoryResponseDto

    @PUT(CATEGORIES)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun updateCategory(
        @Header("Authorization") token: String,
        @Body data: CreateUpdateCategoryRequestDto
    ): CreateUpdateCategoryResponseDto

    @DELETE("$CATEGORIES/{id}")
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun deleteCategory(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): CreateUpdateCategoryResponseDto

    @GET(CUSTOMERS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getCustomers(
        @Header("Authorization") token: String,
    ): CustomerResponseDto

    @POST(CUSTOMERS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun createCustomer(
        @Header("Authorization") token: String,
        @Body data: CreateUpdateCustomerRequestDto
    ): CreateUpdateCustomerResponseDto

    @PUT(CUSTOMERS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun updateCustomer(
        @Header("Authorization") token: String,
        @Body data: CreateUpdateCustomerRequestDto
    ): CreateUpdateCustomerResponseDto

    @DELETE("$CUSTOMERS/{id}")
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun deleteCustomer(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): CreateUpdateCustomerResponseDto

    @POST(TRANSACTIONS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun createTransaction(
        @Header("Authorization") token: String,
        @Body data: CreateTransactionRequestDto
    ): CreateTransactionResponseDto

    @GET(TRANSACTIONS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getTransactions(
        @Header("Authorization") token: String,
        @Query("status") status: String = "",
        @Query("payment_status") paymentStatus: String = "",
    ): TransactionsResponseDto

    @GET("$TRANSACTIONS/{id}")
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getTransaction(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): TransactionResponseDto

    @GET(PAYMENT_METHODS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getPaymentMethods(
        @Header("Authorization") token: String,
    ): PaymentMethodsDto

    @POST(PAYMENT_MAKE)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun makePayment(
        @Header("Authorization") token: String,
        @Body data: MakePaymentRequestDto
    ): MakePaymentResponseDto

    @GET(USERS_LIST)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getUsers(
        @Header("Authorization") token: String,
    ): UsersResponseDto

    @POST(RESET_PASSWORD)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun resetPassword(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): ResetPasswordResponseDto
}