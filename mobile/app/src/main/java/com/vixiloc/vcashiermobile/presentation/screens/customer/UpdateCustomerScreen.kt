package com.vixiloc.vcashiermobile.presentation.screens.customer

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.domain.model.CustomerResponseItem
import com.vixiloc.vcashiermobile.presentation.widgets.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.widgets.commons.Loading
import com.vixiloc.vcashiermobile.presentation.widgets.commons.MessageAlert
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpdateCustomerScreen(
    modifier: Modifier = Modifier,
    viewModel: CustomerViewModel = koinViewModel(),
    navHostController: NavHostController,
    selectedCustomer: CustomerResponseItem?
) {
    val state = viewModel.state
    val events = viewModel::onEvent
    LaunchedEffect(key1 = Unit) {
        selectedCustomer?.let {
            events(
                CustomerEvent.PreFillFormData(
                    id = selectedCustomer.id,
                    name = selectedCustomer.name,
                    number = selectedCustomer.phoneNumber,
                )
            )
        }
    }

    Column(modifier = modifier) {
        CustomerForm(viewModel = viewModel, type = FormType.UPDATE)

        MessageAlert(
            type = AlertType.SUCCESS,
            message = state.success,
            title = "Sukses",
            modifier = Modifier,
            visible = state.success.isNotEmpty(),
            onDismiss = {
                events(CustomerEvent.DismissAlertMessage)
                navHostController.navigateUp()
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