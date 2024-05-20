package com.vixiloc.vcashiermobile.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CreateTransactionRequestDto(
    @SerializedName("customer_id")
    val customerId: Int,
    @SerializedName("items")
    val items: List<ItemDto>,
    @SerializedName("status")
    val status: String
)

data class ItemDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("quantity")
    val quantity: Int
)