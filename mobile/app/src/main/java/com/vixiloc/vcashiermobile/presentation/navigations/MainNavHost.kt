package com.vixiloc.vcashiermobile.presentation.navigations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.vixiloc.vcashiermobile.domain.model.CategoriesResponseItem
import com.vixiloc.vcashiermobile.presentation.screens.category.CategoriesScreen
import com.vixiloc.vcashiermobile.presentation.screens.category.CreateCategoryScreen
import com.vixiloc.vcashiermobile.presentation.screens.category.UpdateCategoryScreen
import com.vixiloc.vcashiermobile.presentation.screens.customer.CreateCustomerScreen
import com.vixiloc.vcashiermobile.presentation.screens.customer.CustomersScreen
import com.vixiloc.vcashiermobile.presentation.screens.home.HomeScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.CreateTransactionScreen
import com.vixiloc.vcashiermobile.presentation.screens.transaction.TransactionReviewScreen

sealed class Screens(val route: String) {
    data object Home : Screens("home")
    data object CreateTransaction : Screens("transaction-create")
    data object TransactionReview : Screens("transaction-review")
    data object Categories : Screens("category") {
        data object AllCategories : Screens("category-all")
        data object CreateCategory : Screens("category-create")
        data object UpdateCategory : Screens("category-update")
    }

    data object Customers : Screens("customers") {
        data object AllCustomers : Screens("customers-all")
        data object CreateCustomer : Screens("customers-create")
        data object UpdateCustomer : Screens("customers-update")

    }
}

@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier) {
    var selectedCategory: CategoriesResponseItem? by remember {
        mutableStateOf(null)
    }
    NavHost(navController = navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) {
            HomeScreen(navigator = navController, modifier = modifier)
        }
        composable(Screens.CreateTransaction.route) {
            CreateTransactionScreen(navigator = navController, modifier = modifier)
        }
        composable(Screens.TransactionReview.route) {
            TransactionReviewScreen(navigator = navController)
        }
        navigation(
            startDestination = Screens.Categories.AllCategories.route,
            route = Screens.Categories.route
        ) {
            composable(Screens.Categories.AllCategories.route) {
                CategoriesScreen(
                    navController = navController,
                    modifier = modifier,
                    onUpdateCategory = { selectedCategory = it }
                )
            }
            composable(Screens.Categories.CreateCategory.route) {
                CreateCategoryScreen(navController = navController, modifier = modifier)
            }
            composable(Screens.Categories.UpdateCategory.route) {
                UpdateCategoryScreen(
                    navHostController = navController,
                    selectedCategory = selectedCategory,
                    modifier = modifier
                )
            }
        }

        navigation(
            startDestination = Screens.Customers.AllCustomers.route,
            route = Screens.Customers.route
        ) {
            composable(Screens.Customers.AllCustomers.route) {
                CustomersScreen(navController = navController, modifier = modifier)
            }
            composable(Screens.Customers.CreateCustomer.route) {
                CreateCustomerScreen(navController = navController, modifier = modifier)
            }
            composable(Screens.Customers.UpdateCustomer.route) {
                // UpdateCustomerScreen(navController = navController, modifier = modifier)
            }
        }
    }
}