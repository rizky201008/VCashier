package com.vixiloc.vcashiermobile.presentation.navs.hosts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem
import com.vixiloc.vcashiermobile.presentation.navs.hosts.sidebar.SidebarHost
import com.vixiloc.vcashiermobile.presentation.navs.hosts.sidebar.SidebarViewModel
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.login.LoginFormScreen
import com.vixiloc.vcashiermobile.presentation.screens.product_log.list_logs.ProductLogScreen
import com.vixiloc.vcashiermobile.presentation.screens.product_log.add_logs.AddProductLogScreen
import com.vixiloc.vcashiermobile.presentation.screens.products.create_product.CreateProductScreen
import com.vixiloc.vcashiermobile.presentation.screens.products.update_product.UpdateProductScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.checkout.CheckoutScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction.CreateTransactionScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.customer.SearchCustomerScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.pay_transaction.PayTransactionScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.process_payment.TransactionPaymentScreen
import com.vixiloc.vcashiermobile.presentation.screens.welcome.WelcomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val sidebarNavController = rememberNavController()
    val sideBarViewModel: SidebarViewModel = koinViewModel()
    NavHost(navController = navHostController, startDestination = MainRoutes.SplashScreen) {
        composable<MainRoutes.SplashScreen> {
            WelcomeScreen(navHostController = navHostController)
        }
        composable<MainRoutes.LoginScreen> {
            LoginFormScreen(navHostController = navHostController)
        }
        composable<MainRoutes.NavDrawerScreens.Transactions.Checkout> { navBackStackEntry ->
            val customer =
                navBackStackEntry.savedStateHandle.get<CustomerResponseItem>("customer")
            CheckoutScreen(navigator = navHostController, customer = customer)
        }
        composable<MainRoutes.NavDrawerScreens.Transactions.PayTransaction> {
            val args = it.toRoute<MainRoutes.NavDrawerScreens.Transactions.PayTransaction>()
            PayTransactionScreen(navigator = navHostController, navArgs = args)
        }
        composable<MainRoutes.NavDrawerScreens> {
            SidebarHost(
                navHostController = sidebarNavController,
                onNavigate = { nav ->
                    if (nav == MainRoutes.LoginScreen) {
                        navHostController.popBackStack()
                        navHostController.navigate(MainRoutes.SplashScreen)
                    } else {
                        navHostController.navigate(nav)
                    }
                },
                modifier = modifier,
                viewModel = sideBarViewModel
            )
        }
        composable<MainRoutes.NavDrawerScreens.Transactions.SearchCustomer> {
            SearchCustomerScreen(
                navController = navHostController,
                modifier = modifier
            )
        }
        composable<MainRoutes.NavDrawerScreens.Transactions.MakePayment> {
            val args = it.toRoute<MainRoutes.NavDrawerScreens.Transactions.MakePayment>()
            TransactionPaymentScreen(
                navigator = navHostController,
                navArgs = args,
                modifier = modifier
            )
        }
        composable<MainRoutes.NavDrawerScreens.Products.CreateProduct> {
            CreateProductScreen(navController = navHostController, modifier = modifier)
        }
        composable<MainRoutes.NavDrawerScreens.Products.UpdateProduct> {
            val args = it.toRoute<MainRoutes.NavDrawerScreens.Products.UpdateProduct>()
            UpdateProductScreen(navController = navHostController, args = args)
        }
        composable<MainRoutes.NavDrawerScreens.Products.ProductLog> {
            ProductLogScreen(navController = navHostController)
        }
        composable<MainRoutes.NavDrawerScreens.Products.ProductLogAdd> {
            AddProductLogScreen(navController = navHostController)
        }
        composable<MainRoutes.NavDrawerScreens.Transactions.CreateTransaction> {
            CreateTransactionScreen(navHostController = navHostController)
        }
    }
}















