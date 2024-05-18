package com.vixiloc.vcashiermobile.presentation.screens.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.presentation.navigations.Screens
import com.vixiloc.vcashiermobile.presentation.widgets.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.widgets.commons.Loading
import com.vixiloc.vcashiermobile.presentation.widgets.commons.MessageAlert
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpdateProductScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ProductsViewModel = koinViewModel(),
    args: Screens.Products.UpdateProduct
) {
    val state = viewModel.state
    val onEvent = viewModel::onEvent

    LaunchedEffect(key1 = Unit) {
        viewModel.processGetProduct(id = args.id)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {

        ProductForm(
            viewModel = viewModel,
            type = FormType.UPDATE
        )
        Loading(modifier = Modifier, visible = state.isLoading)

        MessageAlert(
            type = AlertType.ERROR,
            message = state.error,
            title = "Error",
            modifier = Modifier,
            visible = state.error.isNotBlank(),
            onDismiss = { onEvent(ProductEvent.DismissAlertMessage) }
        )

        MessageAlert(
            type = AlertType.SUCCESS,
            message = state.success,
            title = "Success",
            modifier = Modifier,
            visible = state.success.isNotBlank(),
            onDismiss = {
                onEvent(ProductEvent.DismissAlertMessage)
                navController.navigateUp()
            }
        )
    }
}