package com.vixiloc.vcashiermobile.domain.model.customers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class CustomerResponse(
    val data: List<CustomerResponseItem> = emptyList()
)

@Parcelize
data class CustomerResponseItem(
    val name: String,
    val phoneNumber: String? = null,
    val id: Int
) : Parcelable