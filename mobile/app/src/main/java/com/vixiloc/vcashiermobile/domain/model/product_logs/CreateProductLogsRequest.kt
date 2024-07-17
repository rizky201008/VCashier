package com.vixiloc.vcashiermobile.domain.model.product_logs

import com.vixiloc.vcashiermobile.data.remote.dto.product_logs.CreateProductLogsRequestDto

data class CreateProductLogsRequest(
    val information: String,
    val type: String,
    val amount: Int,
    val product_variation_id: Int
)

fun CreateProductLogsRequest.toDto() =
    CreateProductLogsRequestDto(
        information = information,
        type = type,
        amount = amount,
        product_variation_id = product_variation_id
    )
