package com.vixiloc.vcashiermobile.domain.model.screen

import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.FaIcons
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes

data class HomeMenu(
    val name: String,
    val icon: FaIconType,
    val route: MainRoutes,
    val roles: List<String>
)

val listHomeMenus = listOf(
    HomeMenu(
        name = "Produk",
        icon = FaIcons.Boxes,
        route = MainRoutes.NavDrawerScreens.Products,
        roles = listOf("admin", "warehouse")
    ),
    HomeMenu(
        name = "Kategori",
        icon = FaIcons.Tags,
        route = MainRoutes.NavDrawerScreens.Categories,
        roles = listOf("admin", "warehouse")
    ),
    HomeMenu(
        name = "Transaksi",
        icon = FaIcons.CashRegister,
        route = MainRoutes.NavDrawerScreens.Transactions,
        roles = listOf("admin", "cashier")
    ),
    HomeMenu(
        name = "Pelanggan",
        icon = FaIcons.Users,
        route = MainRoutes.NavDrawerScreens.Customers,
        roles = listOf("admin", "cashier")
    ),
    HomeMenu(
        name = "Pegawai",
        icon = FaIcons.UserAlt,
        route = MainRoutes.NavDrawerScreens.Employees,
        roles = listOf("admin")
    ),
)

fun getListHomeMenus(role: String): List<HomeMenu> {
    return when (role) {
        "admin" -> listHomeMenus
        "cashier" -> listHomeMenus.filter {
            it.roles.contains("cashier")
        }

        "warehouse" -> listHomeMenus.filter {
            it.roles.contains("warehouse")
        }

        else -> {
            emptyList()
        }
    }
}