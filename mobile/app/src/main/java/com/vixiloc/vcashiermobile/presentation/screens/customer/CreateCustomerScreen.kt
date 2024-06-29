package com.vixiloc.vcashiermobile.presentation.screens.customer

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import org.koin.androidx.compose.koinViewModel


@Composable
fun CreateCustomerScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CustomerViewModel = koinViewModel()
) {
    val state = viewModel.state
    val events = viewModel::onEvent

    Column(modifier = modifier) {
        CustomerForm(viewModel = viewModel, type = FormType.CREATE)

        MessageAlert(
            type = AlertType.SUCCESS,
            message = state.success,
            title = "Sukses",
            modifier = Modifier,
            visible = state.success.isNotEmpty(),
            onDismiss = {
                events(CustomerEvent.DismissAlertMessage)
                navController.navigateUp()
            }
        )
        MessageAlert(
            type = AlertType.ERROR,
            message = state.error,
            title = "Error",
            modifier = Modifier,
            visible = state.error.isNotEmpty(),
            onDismiss = {
                events(CustomerEvent.DismissAlertMessage)
            }
        )

        Loading(modifier = Modifier, visible = state.isLoading)
    }
}