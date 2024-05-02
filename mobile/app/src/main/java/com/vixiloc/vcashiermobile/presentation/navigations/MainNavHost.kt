package com.vixiloc.vcashiermobile.presentation.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.vixiloc.vcashiermobile.presentation.screens.category.CategoriesScreen
import com.vixiloc.vcashiermobile.presentation.screens.category.CreateCategoryScreen
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
    }
}

@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier) {
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
                CategoriesScreen(navController = navController, modifier = modifier)
            }
            composable(Screens.Categories.CreateCategory.route) {
                CreateCategoryScreen(navController = navController, modifier = modifier)
            }
        }
    }
}