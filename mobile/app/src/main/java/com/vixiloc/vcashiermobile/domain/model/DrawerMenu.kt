package com.vixiloc.vcashiermobile.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ViewList
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Square
import androidx.compose.ui.graphics.vector.ImageVector
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes

data class DrawerMenu(
    val name: String,
    val icon: ImageVector,
    val route: MainRoutes,
    val role: List<String> = listOf("admin")
)

val listDrawer = listOf(
    DrawerMenu(
        name = "Home",
        icon = Icons.Outlined.Home,
        route = MainRoutes.NavDrawerScreens.Home,
        role = listOf("admin", "cashier", "warehouse")
    ),
    DrawerMenu(
        name = "Transaksi",
        icon = Icons.AutoMirrored.Outlined.ViewList,
        route = MainRoutes.NavDrawerScreens.Transactions,
        role = listOf("admin", "cashier")
    ),
    DrawerMenu(
        name = "Kategori",
        icon = Icons.Outlined.ShoppingCart,
        route = MainRoutes.NavDrawerScreens.Categories,
        role = listOf("admin", "warehouse")
    ),
    DrawerMenu(
        name = "Pelanggan",
        icon = Icons.Outlined.Person,
        route = MainRoutes.NavDrawerScreens.Customers,
        role = listOf("admin", "cashier")
    ),
    DrawerMenu(
        name = "Produk",
        icon = Icons.Outlined.Square,
        route = MainRoutes.NavDrawerScreens.Products,
        role = listOf("admin", "warehouse")
    ),
    DrawerMenu(
        name = "Pegawai",
        icon = Icons.Outlined.PeopleOutline,
        route = MainRoutes.NavDrawerScreens.Employees,
        role = listOf("admin")
    )
)

fun getListDrawer(role: String): List<DrawerMenu> {
    return when (role) {
        "admin" -> listDrawer
        "cashier" -> listDrawer.filter {
            it.role.contains("cashier")
        }

        "warehouse" -> listDrawer.filter {
            it.role.contains("warehouse")
        }

        else -> {
            emptyList()
        }
    }
}