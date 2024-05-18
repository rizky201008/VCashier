package com.vixiloc.vcashiermobile.domain.model

data class ProductResponse(
    val category: Category,
    val categoryId: Int,
    val description: String,
    val id: Int,
    val imagePath: String?,
    val imageUrl: String?,
    val name: String,
    val variations: List<Variation>
)