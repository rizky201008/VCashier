package com.vixiloc.vcashiermobile.domain.model.transactions

import com.vixiloc.vcashiermobile.data.remote.dto.transactions.CreateTransactionRequestDto
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.ItemRequestDto

data class CreateTransactionRequest(
    val customerId: Int,
    val items: List<CreateTransactionItem>,
    val status: String = "draft"
)

data class CreateTransactionItem(
    val id: Int,
    val grocery: Boolean = false,
    val quantity: Int
)

fun CreateTransactionItem.toDto() = ItemRequestDto(
    id = id,
    grocery = grocery,
    quantity = quantity
)

fun CreateTransactionRequest.toDto() = CreateTransactionRequestDto(
    customerId = customerId,
    items = items.map { it.toDto() },
    status = status
)