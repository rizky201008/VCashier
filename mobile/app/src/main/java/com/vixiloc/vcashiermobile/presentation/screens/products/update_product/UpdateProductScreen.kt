package com.vixiloc.vcashiermobile.presentation.screens.products.update_product

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.products.list_products.ProductsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpdateProductScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ProductsViewModel = koinViewModel(),
    args: MainRoutes.NavDrawerScreens.Products.UpdateProduct
) {

}