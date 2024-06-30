package com.vixiloc.vcashiermobile.presentation.screens.category

import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem

data class CategoryState(
    val isLoading: Boolean = false,
    val error: String = "",
    val categories: List<CategoriesResponseItem> = emptyList(),
    val categoryName: String = "",
    val categoryId: Int? = null,
    val success: String = "",
    val searchQuery: String = "",
    val showCreateModal: Boolean = false,
    val showUpdateModal: Boolean = false,
    val showDeleteModal: Boolean = false
)