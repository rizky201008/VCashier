package com.vixiloc.vcashiermobile.domain.model

import com.vixiloc.vcashiermobile.data.remote.dto.CreateTransactionRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.ItemDto

data class CreateTransactionRequest(
    val customerId: Int,
    val items: List<Item>,
    val status: String = "draft"
)

data class Item(
    val id: Int,
    val quantity: Int
)

fun Item.toDto() = ItemDto(
    id = id,
    quantity = quantity
)

fun CreateTransactionRequest.toDto() = CreateTransactionRequestDto(
    customerId = customerId,
    items = items.map { it.toDto() },
    status = status
)