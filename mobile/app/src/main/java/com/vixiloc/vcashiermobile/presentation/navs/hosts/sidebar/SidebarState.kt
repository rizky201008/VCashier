package com.vixiloc.vcashiermobile.presentation.navs.hosts.sidebar

data class SidebarState(
    val isLoading: Boolean = false,
    val error: String = "",
    val showError: Boolean = false,
    val success: String = "",
    val showSuccess: Boolean = false,
    val showLogoutDialog: Boolean = false,
    val pageTitle: String = "Home"
)
