package com.vixiloc.vcashiermobile.presentation.screens.customer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.domain.model.CustomerResponseItem
import com.vixiloc.vcashiermobile.presentation.navigations.Screens
import com.vixiloc.vcashiermobile.presentation.widgets.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.widgets.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.widgets.commons.Loading
import com.vixiloc.vcashiermobile.presentation.widgets.commons.MessageAlert
import com.vixiloc.vcashiermobile.presentation.widgets.commons.TextField
import com.vixiloc.vcashiermobile.presentation.widgets.commons.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.widgets.customer.CustomerItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun CustomersScreen(
    modifier: Modifier = Modifier,
    viewModel: CustomerViewModel = koinViewModel(),
    navController: NavHostController,
    onUpdateCustomer: (CustomerResponseItem?) -> Unit
) {
    val state = viewModel.state
    val events = viewModel::onEvent

    LaunchedEffect(Unit) {
        viewModel.getAllCustomers()
    }
    ConstraintLayout(modifier = modifier) {
        val (searchInput, categories, addButton) = createRefs()

        TextField(
            value = "",
            onValueChanged = {

            },
            modifier = Modifier.constrainAs(searchInput) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            title = "Cari",
            textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )

        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .constrainAs(categories) {
                top.linkTo(searchInput.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            items(state.customers) { customer: CustomerResponseItem ->
                CustomerItem(
                    headlineText = customer.name,
                    supportingText = customer.phoneNumber ?: "-",
                    modifier = Modifier.padding(10.dp),
                    onDelete = {

                    },
                    onUpdate = {
                        onUpdateCustomer(customer)
                        navController.navigate(Screens.Customers.UpdateCustomer.route)
                    },
                    onClick = {

                    }
                )
            }
            item { VerticalSpacer(height = 300.dp, modifier = Modifier) }
        }

        FilledButton(
            onClick = {
                navController.navigate(Screens.Customers.CreateCustomer.route)
            },
            text = "Tambah Pelanggan",
            modifier = Modifier.constrainAs(addButton) {
                top.linkTo(parent.bottom)
                start.linkTo(parent.start, margin = 10.dp)
                end.linkTo(parent.end, margin = 10.dp)
                width = Dimension.matchParent
            })

        Loading(modifier = Modifier, visible = state.isLoading)

        MessageAlert(
            type = AlertType.ERROR,
            message = state.error,
            title = "Error",
            modifier = Modifier,
            visible = state.error.isNotBlank(),
            onDismiss = { events(CustomerEvent.DismissAlertMessage) }
        )

        MessageAlert(
            type = AlertType.SUCCESS,
            message = state.success,
            title = "Success",
            modifier = Modifier,
            visible = state.success.isNotBlank(),
            onDismiss = { events(CustomerEvent.DismissAlertMessage) }
        )

//        MessageAlert(
//            type = AlertType.WARNING,
//            message = state.confirmationMessage,
//            title = "Hapus Pelanggan?",
//            visible = state.confirmationMessage.isNotBlank(),
//            modifier = Modifier,
//            confirmButton = {
//                FilledButton(
//                    onClick = { events(CustomerEvent.ProcessDeleteCategory) },
//                    text = "Ya",
//                    modifier = Modifier
//                )
//            },
//            dismissButton = {
//                FilledButton(
//                    onClick = { events(CustomerEvent.DismissAlertMessage) },
//                    text = "Tidak",
//                    modifier = Modifier
//                )
//            }
//        )

    }
}