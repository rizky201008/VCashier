package com.vixiloc.vcashiermobile.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.vixiloc.vcashiermobile.presentation.navigations.ScreensOld

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
    HOME(ScreensOld.Home),
    CREATE_TRANSACTION(ScreensOld.Transactions),
    CATEGORIES(ScreensOld.Categories),
    CUSTOMERS(ScreensOld.Customers),
    PRODUCTS(ScreensOld.Products),
}
