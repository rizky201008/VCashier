package com.vixiloc.vcashiermobile.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerMenu(
    val name: DrawerMenuName,
    val icon: ImageVector
)

enum class DrawerMenuName(val menuName: String) {
    HOME("Home")
}
