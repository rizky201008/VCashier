package com.vixiloc.vcashiermobile.presentation.screens.category

sealed class CategoryEvent {
    data object DismissAlertMessage : CategoryEvent()
    data class InputCategoryName(val name: String) : CategoryEvent()
    data object SubmitCreateCategory : CategoryEvent()
}