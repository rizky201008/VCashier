package com.vixiloc.vcashiermobile.presentation.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.domain.model.DrawerMenu
import com.vixiloc.vcashiermobile.domain.model.DrawerMenuName
import com.vixiloc.vcashiermobile.domain.model.DrawerMenuRoute
import com.vixiloc.vcashiermobile.presentation.navigations.MainNavHost
import com.vixiloc.vcashiermobile.presentation.components.commons.IconButton
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = listOf(
        DrawerMenu(
            name = DrawerMenuName.HOME,
            icon = Icons.Outlined.Home,
            route = DrawerMenuRoute.HOME.route
        ),
        DrawerMenu(
            name = DrawerMenuName.CREATE_TRANSACTION,
            icon = Icons.Outlined.ShoppingCart,
            route = DrawerMenuRoute.CREATE_TRANSACTION.route
        ),
        DrawerMenu(
            name = DrawerMenuName.CATEGORIES,
            icon = Icons.Outlined.AllInbox,
            route = DrawerMenuRoute.CATEGORIES.route
        ),
        DrawerMenu(
            name = DrawerMenuName.CUSTOMERS,
            icon = Icons.Outlined.Group,
            route = DrawerMenuRoute.CUSTOMERS.route
        ),
        DrawerMenu(
            name = DrawerMenuName.PRODUCTS,
            icon = Icons.Outlined.AllInbox,
            route = DrawerMenuRoute.PRODUCTS.route
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                IconButton(
                    onClick = { scope.launch { drawerState.close() } },
                    icon = Icons.Outlined.Close,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.End)
                )
                Spacer(Modifier.height(12.dp))
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.name.menuName) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navHostController.navigate(item.route) {
                                popUpTo(navHostController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(title = {
                        Text(
                            text = "VCashier",
                            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
                        )
                    }, navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }, icon = Icons.Outlined.Menu)
                    })
                }
            ) { paddingValues ->
                MainNavHost(
                    navController = navHostController,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    )
}