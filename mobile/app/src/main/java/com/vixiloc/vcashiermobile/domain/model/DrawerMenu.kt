package com.vixiloc.vcashiermobile.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.vixiloc.vcashiermobile.presentation.navigations.Screens

data class DrawerMenu(
    val name: DrawerMenuName,
    val icon: ImageVector,
    val route: Any
)

enum class DrawerMenuName(val menuName: String) {
    HOME("Home"),
    CREATE_TRANSACTION("Transaksi"),
    CATEGORIES("Kategori"),
    CUSTOMERS("Pelanggan"),
    PRODUCTS("Produk"),
}

enum class DrawerMenuRoute(val route: Any) {
    HOME(Screens.Home),
    CREATE_TRANSACTION(Screens.Transactions),
    CATEGORIES(Screens.Categories),
    CUSTOMERS(Screens.Customers),
    PRODUCTS(Screens.Products),
}
