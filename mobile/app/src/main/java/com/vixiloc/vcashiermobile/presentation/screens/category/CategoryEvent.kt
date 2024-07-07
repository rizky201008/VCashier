package com.vixiloc.vcashiermobile.presentation.screens.category

import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem

sealed class CategoryEvent {
    data object DismissAlertMessage : CategoryEvent()
    data class InputCategoryName(val name: String) : CategoryEvent()
    data object SubmitCreateCategory : CategoryEvent()
    data object SubmitUpdateCategory : CategoryEvent()
    data class PreFillFormData(val data: CategoriesResponseItem) : CategoryEvent()
    data class SelectCategory(val data: CategoriesResponseItem?) : CategoryEvent()
    data class InputSearchValue(val query: String) : CategoryEvent()
    data class ShowCreateModal(val show: Boolean) : CategoryEvent()
    data class ShowUpdateModal(val show: Boolean) : CategoryEvent()
}