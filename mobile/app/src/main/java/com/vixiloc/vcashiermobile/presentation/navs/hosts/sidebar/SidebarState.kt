package com.vixiloc.vcashiermobile.presentation.navs.hosts.sidebar

import com.vixiloc.vcashiermobile.domain.model.DrawerMenu

data class SidebarState(
    val isLoading: Boolean = false,
    val error: String = "",
    val showError: Boolean = false,
    val success: String = "",
    val showSuccess: Boolean = false,
    val showLogoutDialog: Boolean = false,
    val pageTitle: String = "Home",
    val role: String = "",
    val listDrawer: List<DrawerMenu> = emptyList()
)
