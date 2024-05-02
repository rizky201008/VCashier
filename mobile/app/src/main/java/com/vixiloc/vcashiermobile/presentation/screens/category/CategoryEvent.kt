package com.vixiloc.vcashiermobile.presentation.screens.category

sealed class CategoryEvent {
    data object DismissAlertMessage : CategoryEvent()
}