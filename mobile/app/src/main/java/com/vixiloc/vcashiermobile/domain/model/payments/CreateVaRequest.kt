package com.vixiloc.vcashiermobile.domain.model.payments

import com.vixiloc.vcashiermobile.data.remote.dto.payments.CreateVaRequestDto

data class CreateVaRequest(
    val transactionId: String,
)

fun CreateVaRequest.toDto() = CreateVaRequestDto(
    transactionId = transactionId,
)