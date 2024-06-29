package com.vixiloc.vcashiermobile.presentation.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem
import com.vixiloc.vcashiermobile.presentation.screens.category.CategoriesScreen
import com.vixiloc.vcashiermobile.presentation.screens.category.CreateCategoryScreen
import com.vixiloc.vcashiermobile.presentation.screens.category.UpdateCategoryScreen
import com.vixiloc.vcashiermobile.presentation.screens.customer.CreateCustomerScreen
import com.vixiloc.vcashiermobile.presentation.screens.customer.CustomersScreen
import com.vixiloc.vcashiermobile.presentation.screens.customer.UpdateCustomerScreen
import com.vixiloc.vcashiermobile.presentation.screens.home.HomeScreen
import com.vixiloc.vcashiermobile.presentation.screens.products.CreateProductScreen
import com.vixiloc.vcashiermobile.presentation.screens.products.ProductsScreen
import com.vixiloc.vcashiermobile.presentation.screens.products.UpdateProductScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.customer.SearchCustomerScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.TransactionsScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.checkout.CheckoutScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction.CreateTransactionScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.pay_transaction.PayTransactionScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.transaction_payment.TransactionPaymentScreen
import kotlinx.serialization.Serializable

sealed interface ScreensOld {
    @Serializable
    data object Home : ScreensOld

    @Serializable
    data object Transactions : ScreensOld {
        @Serializable
        data object CreateTransaction : ScreensOld

        @Serializable
        data object AllTransactions : ScreensOld
    }

    @Serializable
    data object Categories : ScreensOld {
        @Serializable
        data object AllCategories : ScreensOld

        @Serializable
        data object CreateCategory : ScreensOld

        @Serializable
        data class UpdateCategory(
            val name: String,
            val id: Int
        ) : ScreensOld
    }

    @Serializable
    data object Customers : ScreensOld {
        @Serializable
        data object AllCustomers : ScreensOld

        @Serializable
        data object CreateCustomer : ScreensOld

        @Serializable
        data class UpdateCustomer(
            val name: String,
            val phoneNumber: String? = null,
            val id: Int
        ) : ScreensOld
    }

    @Serializable
    data object Products : ScreensOld {
        @Serializable
        data object AllProducts : ScreensOld

        @Serializable
        data object CreateProduct : ScreensOld

        @Serializable
        data class UpdateProduct(val id: String) : ScreensOld
    }

}

sealed interface CheckoutScreens {
    @Serializable
    data object Checkout : CheckoutScreens

    @Serializable
    data class PayTransaction(val id: String) : CheckoutScreens

    @Serializable
    data object SearchCustomer : CheckoutScreens

    @Serializable
    data class MakePayment(val transactionId: String) : CheckoutScreens
}

@Composable
fun NavHostsOld(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = ScreensOld.Home) {
        composable<ScreensOld.Home> {
            HomeScreen(navigator = navController, modifier = modifier)
        }
        navigation<ScreensOld.Transactions>(startDestination = ScreensOld.Transactions.AllTransactions) {
            composable<ScreensOld.Transactions.CreateTransaction> {
                CreateTransactionScreen(
                    navigator = navController,
                    modifier = modifier,
                )
            }

            composable<ScreensOld.Transactions.AllTransactions> {
                TransactionsScreen(navHostController = navController, modifier = modifier)
            }

        }
        navigation<ScreensOld.Categories>(
            startDestination = ScreensOld.Categories.AllCategories,
        ) {
            composable<ScreensOld.Categories.AllCategories> {
                CategoriesScreen(
                    navController = navController,
                    modifier = modifier
                )
            }
            composable<ScreensOld.Categories.CreateCategory> {
                CreateCategoryScreen(navController = navController, modifier = modifier)
            }
            composable<ScreensOld.Categories.UpdateCategory> {
                val args = it.toRoute<ScreensOld.Categories.UpdateCategory>()
                UpdateCategoryScreen(
                    navHostController = navController,
                    modifier = modifier,
                    navArgs = args
                )
            }
        }

        navigation<ScreensOld.Customers>(
            startDestination = ScreensOld.Customers.AllCustomers
        ) {
            composable<ScreensOld.Customers.AllCustomers> {
                CustomersScreen(
                    navController = navController,
                    modifier = modifier
                )
            }
            composable<ScreensOld.Customers.CreateCustomer> {
                CreateCustomerScreen(navController = navController, modifier = modifier)
            }
            composable<ScreensOld.Customers.UpdateCustomer> {
                val args = it.toRoute<ScreensOld.Customers.UpdateCustomer>()
                UpdateCustomerScreen(
                    navHostController = navController,
                    modifier = modifier,
                    navArgs = args
                )
            }
        }

        navigation<ScreensOld.Products>(
            startDestination = ScreensOld.Products.AllProducts,
        ) {
            composable<ScreensOld.Products.AllProducts> {
                ProductsScreen(modifier = modifier, navController = navController)
            }
            composable<ScreensOld.Products.CreateProduct> {
                CreateProductScreen(navController = navController, modifier = modifier)
            }
            composable<ScreensOld.Products.UpdateProduct> {
                val args = it.toRoute<ScreensOld.Products.UpdateProduct>()
                UpdateProductScreen(navController = navController, modifier = modifier, args = args)
            }
        }
    }
}

@Composable
fun CheckoutNavHostOld(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(navController = navController, startDestination = CheckoutScreens.Checkout) {
        composable<CheckoutScreens.Checkout> { navBackStackEntry ->
            val customer =
                navBackStackEntry.savedStateHandle.get<CustomerResponseItem>("customer")
            CheckoutScreen(navigator = navController, modifier = modifier, customer = customer)
        }
        composable<CheckoutScreens.PayTransaction> {
            val args = it.toRoute<CheckoutScreens.PayTransaction>()
            PayTransactionScreen(
                navigator = navController,
                navArgs = args,
                modifier = modifier
            )
        }
        composable<CheckoutScreens.SearchCustomer> {
            SearchCustomerScreen(
                navController = navController,
                modifier = modifier
            )
        }
        composable<CheckoutScreens.MakePayment> {
            val args = it.toRoute<CheckoutScreens.MakePayment>()
            TransactionPaymentScreen(
                navigator = navController,
                navArgs = args,
                modifier = modifier
            )
        }
    }
}