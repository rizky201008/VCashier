package com.vixiloc.vcashiermobile.domain.model.transactions

data class TransactionsResponse(
    val data: List<TransactionsData>
)

data class TransactionsData(
    val createdAt: String?,
    val customerId: Int,
    val id: String,
    val items: List<TransactionsItem>?,
    val payment: Any?,
    val paymentStatus: String,
    val totalAmount: Int,
    val transactionStatus: String,
    val updatedAt: String?,
    val userId: Int,
    val customer: TransactionsCustomer? = null
)

data class TransactionsProduct(
    val categoryId: Int,
    val imagePath: String? = null,
    val imageUrl: String? = null,
    val name: String,
    val description: String,
    val id: Int,
    val category: TransactionsCategory? = null
)

data class TransactionsCategory(
    val name: String,
    val id: Int
)

data class TransactionsItem(
    val id: Int,
    val price: String,
    val productVariation: ProductVariation? = null,
    val productVariationId: Int,
    val quantity: Int,
    val subtotal: Int,
    val transactionId: String
)

data class ProductVariation(
    val id: Int,
    val price: String,
    val priceGrocery: String,
    val product: TransactionsProduct? = null,
    val productId: Int,
    val stock: Int,
    val unit: String
)

data class TransactionsCustomer(
    val id: Int,
    val name: String,
    val phoneNumber: String? = null
)