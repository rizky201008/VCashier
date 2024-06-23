package com.vixiloc.vcashiermobile.data.remote.dto.transactions


import com.google.gson.annotations.SerializedName

data class CreateTransactionRequestDto(
    @SerializedName("customer_id")
    val customerId: Int,
    @SerializedName("items")
    val items: List<ItemRequestDto>,
    @SerializedName("status")
    val status: String
)

data class ItemRequestDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("quantity")
    val quantity: Int
)