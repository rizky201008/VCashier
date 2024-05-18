package com.vixiloc.vcashiermobile.presentation.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
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
import com.vixiloc.vcashiermobile.presentation.screens.transaction.CreateTransactionScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.TransactionReviewScreen
import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object Home : Screens

    @Serializable
    data object CreateTransaction : Screens

    @Serializable
    data object TransactionReview : Screens

    @Serializable
    data object Categories : Screens {
        @Serializable
        data object AllCategories : Screens

        @Serializable
        data object CreateCategory : Screens

        @Serializable
        data class UpdateCategory(
            val name: String,
            val id: Int
        ) : Screens
    }

    @Serializable
    data object Customers : Screens {
        @Serializable
        data object AllCustomers : Screens

        @Serializable
        data object CreateCustomer : Screens

        @Serializable
        data class UpdateCustomer(
            val name: String,
            val phoneNumber: String? = null,
            val id: Int
        ) : Screens
    }

    @Serializable
    data object Products : Screens {
        @Serializable
        data object AllProducts : Screens

        @Serializable
        data object CreateProduct : Screens

        @Serializable
        data class UpdateProduct(val id: String) : Screens
    }

}

@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier) {

    NavHost(navController = navController, startDestination = Screens.Home) {
        composable<Screens.Home> {
            HomeScreen(navigator = navController, modifier = modifier)
        }
        composable<Screens.CreateTransaction> {
            CreateTransactionScreen(navigator = navController, modifier = modifier)
        }
        composable<Screens.TransactionReview> {
            TransactionReviewScreen(navigator = navController)
        }
        navigation<Screens.Categories>(
            startDestination = Screens.Categories.AllCategories,
        ) {
            composable<Screens.Categories.AllCategories> {
                CategoriesScreen(
                    navController = navController,
                    modifier = modifier
                )
            }
            composable<Screens.Categories.CreateCategory> {
                CreateCategoryScreen(navController = navController, modifier = modifier)
            }
            composable<Screens.Categories.UpdateCategory> {
                val args = it.toRoute<Screens.Categories.UpdateCategory>()
                UpdateCategoryScreen(
                    navHostController = navController,
                    modifier = modifier,
                    navArgs = args
                )
            }
        }

        navigation<Screens.Customers>(
            startDestination = Screens.Customers.AllCustomers
        ) {
            composable<Screens.Customers.AllCustomers> {
                CustomersScreen(
                    navController = navController,
                    modifier = modifier
                )
            }
            composable<Screens.Customers.CreateCustomer> {
                CreateCustomerScreen(navController = navController, modifier = modifier)
            }
            composable<Screens.Customers.UpdateCustomer> {
                val args = it.toRoute<Screens.Customers.UpdateCustomer>()
                UpdateCustomerScreen(
                    navHostController = navController,
                    modifier = modifier,
                    navArgs = args
                )
            }
        }

        navigation<Screens.Products>(
            startDestination = Screens.Products.AllProducts,
        ) {
            composable<Screens.Products.AllProducts> {
                ProductsScreen(modifier = modifier, navController = navController)
            }
            composable<Screens.Products.CreateProduct> {
                CreateProductScreen(navController = navController, modifier = modifier)
            }
            composable<Screens.Products.UpdateProduct> {
                val args = it.toRoute<Screens.Products.UpdateProduct>()
                UpdateProductScreen(navController = navController, modifier = modifier, args = args)
            }
        }
    }
}