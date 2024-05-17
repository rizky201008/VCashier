package com.vixiloc.vcashiermobile.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.vixiloc.vcashiermobile.presentation.navigations.Screens

data class DrawerMenu(
    val name: DrawerMenuName,
    val icon: ImageVector
)

enum class DrawerMenuName(val menuName: String, val route: Any) {
    HOME("Home", route = Screens.Home),
    CREATE_TRANSACTION("Transaksi", route = Screens.CreateTransaction),
    CATEGORIES("Kategori", route = Screens.Categories),
    CUSTOMERS("Pelanggan", route = Screens.Customers),
    PRODUCTS("Produk", route = Screens.Products),
}
