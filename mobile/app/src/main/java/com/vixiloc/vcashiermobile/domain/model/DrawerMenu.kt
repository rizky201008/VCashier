package com.vixiloc.vcashiermobile.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.vixiloc.vcashiermobile.presentation.navigations.Screens

data class DrawerMenu(
    val name: DrawerMenuName,
    val icon: ImageVector
)

enum class DrawerMenuName(val menuName: String, val route: String) {
    HOME("Home", route = Screens.Home.route),
    CREATE_TRANSACTION("Transaksi", route = Screens.CreateTransaction.route),
    CATEGORIES("Kategori", route = Screens.Categories.route)
}
