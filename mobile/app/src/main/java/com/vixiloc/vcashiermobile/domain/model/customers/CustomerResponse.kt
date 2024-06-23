package com.vixiloc.vcashiermobile.domain.model.customers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class CustomerResponse(
    val data: List<CustomerResponseItem> = emptyList()
)

@Parcelize
data class CustomerResponseItem(
    val updatedAt: String? = null,
    val name: String,
    val createdAt: String? = null,
    val phoneNumber: String? = null,
    val id: Int
) : Parcelable