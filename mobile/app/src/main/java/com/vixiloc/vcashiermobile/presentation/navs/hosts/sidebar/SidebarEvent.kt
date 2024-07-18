package com.vixiloc.vcashiermobile.presentation.navs.hosts.sidebar

sealed class SidebarEvent {
    data object Logout : SidebarEvent()
    data class ShowError(val show: Boolean) : SidebarEvent()
    data class ShowSuccess(val show: Boolean) : SidebarEvent()
    data class ShowLogoutDialog(val show: Boolean) : SidebarEvent()
    data class ChangePageTitle(val title: String) : SidebarEvent()
}