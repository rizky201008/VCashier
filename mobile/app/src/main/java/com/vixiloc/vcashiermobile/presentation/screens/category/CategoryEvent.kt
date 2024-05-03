package com.vixiloc.vcashiermobile.presentation.screens.category

sealed class CategoryEvent {
    data object DismissAlertMessage : CategoryEvent()
    data class InputCategoryName(val name: String) : CategoryEvent()
    data object SubmitCreateCategory : CategoryEvent()
    data object SubmitUpdateCategory : CategoryEvent()
    data class PreFillFormData(val id: Int, val name: String) : CategoryEvent()
}