package com.vixiloc.vcashiermobile.presentation.screens.category

import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem

sealed class CategoryEvent {
    data object DismissAlertMessage : CategoryEvent()
    data class InputCategoryName(val name: String) : CategoryEvent()
    data object SubmitCreateCategory : CategoryEvent()
    data object SubmitUpdateCategory : CategoryEvent()
    data class PreFillFormData(val id: Int, val name: String) : CategoryEvent()
    data class DeleteCategory(val data: CategoriesResponseItem) : CategoryEvent()
    data object ProcessDeleteCategory : CategoryEvent()
    data class InputSearchValue(val query: String) : CategoryEvent()
}