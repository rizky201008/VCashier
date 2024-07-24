package com.vixiloc.vcashiermobile.presentation.screens.customer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.SearchTextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.customer.components.CreateUpdateCustomerDialog
import com.vixiloc.vcashiermobile.presentation.screens.customer.components.CustomerItem
import com.vixiloc.vcashiermobile.presentation.screens.customer.components.InputType
import org.koin.androidx.compose.koinViewModel

@Composable
fun CustomersScreen(
    modifier: Modifier = Modifier,
    viewModel: CustomerViewModel = koinViewModel(),
    onNavigate: (MainRoutes) -> Unit
) {
    val state = viewModel.state.value
    val events = viewModel::onEvent
    val focusManager = LocalFocusManager.current

    ConstraintLayout(modifier = modifier) {
        val (content, addButton) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
                .constrainAs(content) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            SearchTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                value = state.searchQuery,
                onValueChanged = {
                    events(CustomerEvent.InputSearchValue(it))
                },
                placeHolder = "Cari pelanggan",
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            ) {
                item { Loading(modifier = Modifier, visible = state.isLoading) }
                items(state.customers) { customer: CustomerResponseItem ->
                    CustomerItem(
                        item = customer,
                        modifier = Modifier.padding(bottom = 10.dp),
                        onUpdate = {
                            events(
                                CustomerEvent.FillFormData(
                                    it
                                )
                            )
                            events(CustomerEvent.ShowUpdateModal(true))
                        },
                        onClick = {

                        }
                    )
                }
                item { VerticalSpacer(height = 300.dp, modifier = Modifier) }
            }
        }

        Box(
            modifier = Modifier.constrainAs(addButton) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start, margin = 10.dp)
                end.linkTo(parent.end, margin = 10.dp)
                width = Dimension.matchParent
            })
        {
            FilledButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    events(CustomerEvent.ShowCreateModal(true))
                },
                text = "Tambah Pelanggan",
            )

        }

        MessageAlert(
            type = AlertType.ERROR,
            message = state.error,
            title = "Error",
            modifier = Modifier,
            visible = state.showError,
            onDismiss = {
                events(CustomerEvent.ShowError(false))
            }
        )

        MessageAlert(
            type = AlertType.SUCCESS,
            message = state.success,
            title = "Success",
            modifier = Modifier,
            visible = state.showSuccess,
            onDismiss = {
                events(CustomerEvent.ShowSuccess(false))
            }
        )

        if (state.showCreateModal) {
            CreateUpdateCustomerDialog(type = InputType.CREATE, viewModel = viewModel)
        }

        if (state.showUpdateModal) {
            CreateUpdateCustomerDialog(type = InputType.UPDATE, viewModel = viewModel)
        }
    }
}