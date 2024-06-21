package com.vixiloc.vcashiermobile.presentation.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vixiloc.vcashiermobile.R
import com.vixiloc.vcashiermobile.domain.model.DrawerMenu
import com.vixiloc.vcashiermobile.domain.model.DrawerMenuName
import com.vixiloc.vcashiermobile.domain.model.DrawerMenuRoute
import com.vixiloc.vcashiermobile.presentation.components.commons.PainterIconButton
import com.vixiloc.vcashiermobile.presentation.navigations.MainNavHost
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

    // MutableState to hold the current title
    val currentTitle = remember { mutableStateOf("VCashier") }

    // Get the current back stack entry and the current route
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Find the matching DrawerMenu item based on the current route
    currentRoute?.let {
        val matchedItem = items.find { it.route == it }
        matchedItem?.let {
            currentTitle.value = it.name.menuName
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                PainterIconButton(
                    onClick = { scope.launch { drawerState.close() } },
                    icon = painterResource(id = R.drawable.hamburger_icon),
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
                            currentTitle.value = item.name.menuName
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
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = currentTitle.value,
                                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight(600))
                            )
                        }, navigationIcon = {
                            PainterIconButton(
                                onClick = {
                                    scope.launch { drawerState.open() }
                                },
                                icon = painterResource(id = R.drawable.hamburger_icon)
                            )
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