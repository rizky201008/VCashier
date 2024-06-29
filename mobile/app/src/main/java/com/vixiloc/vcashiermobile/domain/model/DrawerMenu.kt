package com.vixiloc.vcashiermobile.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ViewList
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Square
import androidx.compose.ui.graphics.vector.ImageVector
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes

data class DrawerMenu(
    val name: String,
    val icon: ImageVector,
    val route: MainRoutes
)

val listDrawer = listOf(
    DrawerMenu(name = "Home", icon = Icons.Outlined.Home, route = MainRoutes.NavDrawerScreens.Home),
    DrawerMenu(
        name = "Transaksi",
        icon = Icons.AutoMirrored.Outlined.ViewList,
        route = MainRoutes.NavDrawerScreens.Transactions
    ),
    DrawerMenu(
        name = "Kategori",
        icon = Icons.Outlined.ShoppingCart,
        route = MainRoutes.NavDrawerScreens.Categories
    ),
    DrawerMenu(
        name = "Pelanggan",
        icon = Icons.Outlined.Person,
        route = MainRoutes.NavDrawerScreens.Customers
    ),
    DrawerMenu(
        name = "Produk",
        icon = Icons.Outlined.Square,
        route = MainRoutes.NavDrawerScreens.Products
    )
)