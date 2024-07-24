package com.vixiloc.vcashiermobile.presentation.navs.hosts.sidebar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.guru.fontawesomecomposelib.FaIcons
import com.vixiloc.vcashiermobile.R
import com.vixiloc.vcashiermobile.domain.model.listDrawer
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.PainterIconButton
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.category.CategoriesScreen
import com.vixiloc.vcashiermobile.presentation.screens.customer.CustomersScreen
import com.vixiloc.vcashiermobile.presentation.screens.employee.EmployeesScreen
import com.vixiloc.vcashiermobile.presentation.screens.home.HomeScreen
import com.vixiloc.vcashiermobile.presentation.screens.products.list_products.ProductsScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions.TransactionsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SidebarHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onNavigate: (MainRoutes) -> Unit,
    viewModel: SidebarViewModel
) {
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    currentRoute?.let { route ->
        val matchedItem = listDrawer.find {
            it.name == route
        }

        matchedItem?.let {
            onEvent(SidebarEvent.ChangePageTitle(it.name))
        }
    }

    ModalNavigationDrawer(
        modifier = modifier,
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
                listDrawer.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.name) },
                        selected = item.name == state.pageTitle,
                        onClick = {
                            scope.launch { drawerState.close() }
                            onEvent(SidebarEvent.ChangePageTitle(item.name))
                            navHostController.navigate(item.route) {
                                popUpTo(navHostController.graph.findStartDestination().id)
                                launchSingleTop = true
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
                                text = state.pageTitle,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight(600)
                                )
                            )
                        },
                        navigationIcon = {
                            PainterIconButton(
                                onClick = {
                                    scope.launch { drawerState.open() }
                                },
                                icon = painterResource(id = R.drawable.hamburger_icon)
                            )
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    onEvent(SidebarEvent.ShowLogoutDialog(true))
                                },
                                icon = FaIcons.SignOutAlt
                            )
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.White,
                        )
                    )
                }
            ) { paddingValues ->
                val screenModifier = Modifier.padding(paddingValues)
                NavHost(
                    navController = navHostController,
                    startDestination = MainRoutes.NavDrawerScreens.Home
                ) {
                    composable<MainRoutes.NavDrawerScreens.Home> {
                        HomeScreen(
                            modifier = screenModifier,
                            onNavigate = onNavigate
                        )
                    }
                    composable<MainRoutes.NavDrawerScreens.Transactions> {
                        TransactionsScreen(
                            modifier = screenModifier,
                            onNavigate = onNavigate,
                            navController = navHostController,
                            onTitleChange = { title ->
                                onEvent(SidebarEvent.ChangePageTitle(title))
                            }
                        )
                    }
                    composable<MainRoutes.NavDrawerScreens.Categories> {
                        CategoriesScreen(
                            modifier = screenModifier
                        )
                    }
                    composable<MainRoutes.NavDrawerScreens.Products> {
                        ProductsScreen(
                            modifier = screenModifier,
                            onNavigate = onNavigate
                        )
                    }
                    composable<MainRoutes.NavDrawerScreens.Customers> {
                        CustomersScreen(
                            modifier = screenModifier,
                            onNavigate = onNavigate
                        )
                    }
                    composable<MainRoutes.NavDrawerScreens.Employees> {
                        EmployeesScreen(
                            modifier = screenModifier
                        )
                    }
                }
                MessageAlert(
                    type = AlertType.WARNING,
                    message = "Anda yakin ingin logout?",
                    title = "Logout",
                    visible = state.showLogoutDialog,
                    modifier = Modifier,
                    confirmButton = {
                        FilledButton(
                            onClick = {
                                onEvent(SidebarEvent.Logout)
                            },
                            text = "Ya",
                            modifier = Modifier
                        )
                    },
                    dismissButton = {
                        FilledButton(
                            onClick = {
                                onEvent(SidebarEvent.ShowLogoutDialog(false))
                            },
                            text = "Batal",
                            modifier = Modifier
                        )
                    },
                    onDismiss = {
                        onEvent(SidebarEvent.ShowLogoutDialog(false))
                    }
                )
                Loading(modifier = Modifier, visible = state.isLoading)

                MessageAlert(
                    type = AlertType.SUCCESS,
                    message = state.success,
                    title = "Sukses",
                    modifier = Modifier,
                    visible = state.showSuccess,
                    onDismiss = {
                        onEvent(SidebarEvent.ShowSuccess(false))
                        navHostController.popBackStack()
                        onNavigate(MainRoutes.LoginScreen)
                    }
                )
                MessageAlert(
                    type = AlertType.ERROR,
                    message = state.error,
                    title = "Error",
                    modifier = Modifier,
                    visible = state.showError,
                    onDismiss = {
                        onEvent(SidebarEvent.ShowError(false))
                    }
                )
            }
        }
    )
}