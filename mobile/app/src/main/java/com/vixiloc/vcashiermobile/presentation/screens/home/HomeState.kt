package com.vixiloc.vcashiermobile.presentation.screens.home

import com.vixiloc.vcashiermobile.domain.model.screen.HomeMenu

data class HomeState(
    val isLoading: Boolean = false,
    val listHomeMenu: List<HomeMenu> = emptyList(),
    val role: String = ""
)
