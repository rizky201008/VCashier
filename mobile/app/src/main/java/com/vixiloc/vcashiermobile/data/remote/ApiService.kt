package com.vixiloc.vcashiermobile.data.remote

import com.vixiloc.vcashiermobile.data.remote.Routes.ADD_IMAGE
import com.vixiloc.vcashiermobile.data.remote.Routes.CATEGORIES
import com.vixiloc.vcashiermobile.data.remote.Routes.CUSTOMERS
import com.vixiloc.vcashiermobile.data.remote.Routes.GET_PRODUCT
import com.vixiloc.vcashiermobile.data.remote.Routes.LOGIN
import com.vixiloc.vcashiermobile.data.remote.Routes.LOGOUT
import com.vixiloc.vcashiermobile.data.remote.Routes.PAYMENT_CHECK_STATUS
import com.vixiloc.vcashiermobile.data.remote.Routes.PAYMENT_CREATE_VA
import com.vixiloc.vcashiermobile.data.remote.Routes.PAYMENT_MAKE
import com.vixiloc.vcashiermobile.data.remote.Routes.PAYMENT_METHODS
import com.vixiloc.vcashiermobile.data.remote.Routes.PRODUCTS
import com.vixiloc.vcashiermobile.data.remote.Routes.PRODUCT_LOGS_ADD
import com.vixiloc.vcashiermobile.data.remote.Routes.PRODUCT_LOGS_ALL
import com.vixiloc.vcashiermobile.data.remote.Routes.REGISTER
import com.vixiloc.vcashiermobile.data.remote.Routes.RESET_PASSWORD
import com.vixiloc.vcashiermobile.data.remote.Routes.TRANSACTIONS
import com.vixiloc.vcashiermobile.data.remote.Routes.UPDATE_IMAGE
import com.vixiloc.vcashiermobile.data.remote.Routes.USERS_LIST
import com.vixiloc.vcashiermobile.data.remote.Routes.VARIATIONS
import com.vixiloc.vcashiermobile.data.remote.dto.categories.CategoriesResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.CreateProductResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.categories.CreateUpdateCategoryRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.categories.CreateUpdateCategoryResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.customers.CreateUpdateCustomerRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.customers.CreateUpdateCustomerResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.CreateUpdateProductImageResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.CreateProductRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.customers.CustomerResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LoginRegisterResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.LogoutResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.RegisterRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.auth.ResetPasswordResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.CheckPaymentStatusResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.CreateVaRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.CreateVaResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.MakePaymentRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.MakePaymentResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.payments.PaymentMethodsDto
import com.vixiloc.vcashiermobile.data.remote.dto.product_logs.CreateProductLogResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.product_logs.CreateProductLogsRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.product_logs.ProductLogsResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.ProductResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.ProductsResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.UpdateProductRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.TransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.TransactionsResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.UpdateProductResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.UpdateProductVariationRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.products.UpdateProductVariationResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.UpdateTransactionRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.UpdateTransactionResponseDto
import com.vixiloc.vcashiermobile.data.remote.dto.users.UsersResponseDto
import com.vixiloc.vcashiermobile.domain.model.products.UpdateProductVariationRequest
import com.vixiloc.vcashiermobile.domain.model.transactions.UpdateTransactionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
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

    @POST(LOGOUT)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun logout(@Header("Authorization") token: String): LogoutResponseDto

    @GET(PRODUCTS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getProducts(
        @Header("Authorization") token: String,
    ): ProductsResponseDto

    @GET(GET_PRODUCT)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getProduct(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): ProductResponseDto

    @POST(PRODUCTS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun createProduct(
        @Header("Authorization") token: String,
        @Body data: CreateProductRequestDto
    ): CreateProductResponseDto

    @PUT(PRODUCTS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun updateProduct(
        @Header("Authorization") token: String,
        @Body data: UpdateProductRequestDto
    ): UpdateProductResponseDto

    @PUT(VARIATIONS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun updateVariation(
        @Header("Authorization") token: String,
        @Body data: UpdateProductVariationRequestDto
    ): UpdateProductVariationResponseDto

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

    @PUT(TRANSACTIONS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun updateTransaction(
        @Header("Authorization") token: String,
        @Body data: UpdateTransactionRequestDto
    ): UpdateTransactionResponseDto

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

    @POST(PAYMENT_CREATE_VA)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun createVa(
        @Header("Authorization") token: String,
        @Body data: CreateVaRequestDto
    ): CreateVaResponseDto

    @GET(PAYMENT_CHECK_STATUS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun checkPaymentStatus(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): CheckPaymentStatusResponseDto

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

    @GET(PRODUCT_LOGS_ALL)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getProductLogs(
        @Header("Authorization") token: String,
    ): ProductLogsResponseDto

    @POST(PRODUCT_LOGS_ADD)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun addProductLogs(
        @Header("Authorization") token: String,
        @Body data: CreateProductLogsRequestDto
    ): CreateProductLogResponseDto
}