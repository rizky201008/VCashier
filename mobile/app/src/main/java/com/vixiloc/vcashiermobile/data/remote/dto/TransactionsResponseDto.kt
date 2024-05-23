package com.vixiloc.vcashiermobile.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.ProductVariation
import com.vixiloc.vcashiermobile.domain.model.TransactionsCategory
import com.vixiloc.vcashiermobile.domain.model.TransactionsCustomer
import com.vixiloc.vcashiermobile.domain.model.TransactionsData
import com.vixiloc.vcashiermobile.domain.model.TransactionsItem
import com.vixiloc.vcashiermobile.domain.model.TransactionsProduct
import com.vixiloc.vcashiermobile.domain.model.TransactionsResponse

data class TransactionsResponseDto(
    @SerializedName("data")
    val data: List<TransactionsDataDto> = emptyList()
)

data class TransactionsDataDto(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("customer_id")
    val customerId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("items")
    val items: List<TransactionsItemDto>? = emptyList(),
    @SerializedName("payment")
    val payment: Any?,
    @SerializedName("payment_status")
    val paymentStatus: String,
    @SerializedName("total_amount")
    val totalAmount: Int,
    @SerializedName("transaction_status")
    val transactionStatus: String,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("customer")
    val customer: TransactionsCustomerDto? = null
)

data class TransactionsCustomerDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone_number")
    val phoneNumber: String? = null,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)

data class TransactionsItemDto(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_variation")
    val productVariation: ProductVariationDto? = null,
    @SerializedName("product_variation_id")
    val productVariationId: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("subtotal")
    val subtotal: Int,
    @SerializedName("transaction_id")
    val transactionId: Int,
    @SerializedName("updated_at")
    val updatedAt: String?
)

data class ProductVariationDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: String,
    @SerializedName("price_grocery")
    val priceGrocery: String,
    @SerializedName("product")
    val product: TransactionsProductDto? = null,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("unit")
    val unit: String
)

data class TransactionsProductDto(

    @field:SerializedName("category_id")
    val categoryId: Int,

    @field:SerializedName("image_path")
    val imagePath: String? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("category")
    val category: TransactionsCategoryDto? = null
)

data class TransactionsCategoryDto(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)

fun TransactionsCustomerDto.toDomain() = TransactionsCustomer(
    id = id,
    name = name,
    phoneNumber = phoneNumber
)

fun TransactionsCategoryDto.toDomain() = TransactionsCategory(
    name = name,
    id = id
)

fun TransactionsProductDto.toDomain() = TransactionsProduct(
    categoryId = categoryId,
    imagePath = imagePath,
    imageUrl = imageUrl,
    name = name,
    description = description,
    id = id,
    category = category?.toDomain()
)

fun ProductVariationDto.toDomain() = ProductVariation(
    id = id,
    price = price,
    priceGrocery = priceGrocery,
    product = product?.toDomain(),
    productId = productId,
    stock = stock,
    unit = unit
)

fun TransactionsItemDto.toDomain() = TransactionsItem(
    id = id,
    price = price,
    productVariation = productVariation?.toDomain(),
    productVariationId = productVariationId,
    quantity = quantity,
    subtotal = subtotal,
    transactionId = transactionId
)

fun TransactionsDataDto.toDomain() = TransactionsData(
    createdAt = createdAt,
    customerId = customerId,
    id = id,
    items = items?.map { it.toDomain() },
    payment = payment,
    paymentStatus = paymentStatus,
    totalAmount = totalAmount,
    transactionStatus = transactionStatus,
    updatedAt = updatedAt,
    userId = userId,
    customer = customer?.toDomain()
)

fun TransactionsResponseDto.toDomain() = TransactionsResponse(
    data = data.map { it.toDomain() }
)