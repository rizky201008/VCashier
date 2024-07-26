package com.vixiloc.vcashiermobile.presentation.screens.transaction.customer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.SearchTextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.screens.transaction.customer.components.CustomerListItem
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCustomerScreen(modifier: Modifier = Modifier, navController: NavController) {
    val viewModel: SearchCustomerViewModel = koinViewModel()
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                title = {
                    Text(
                        text = "Tambah Pelanggan",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight(600)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                        icon = Icons.Outlined.ArrowBackIosNew
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(25.dp)
        ) {
            SearchTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.searchValue,
                onValueChanged = {
                    onEvent(SearchCustomerEvent.SetSearchValue(it))
                },
                placeHolder = "Cari pengguna",
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            )

            VerticalSpacer(height = 12.dp)

            Loading(modifier = Modifier, visible = state.isLoading)

            LazyColumn {
                items(state.customers) { customer ->
                    CustomerListItem(
                        modifier = Modifier,
                        item = customer,
                        onClick = {
                            navController.popBackStack()
                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("customer", it)
                        }
                    )
                    VerticalSpacer(height = 12.dp)
                }
            }

            MessageAlert(
                type = AlertType.SUCCESS,
                message = state.success,
                title = "Sukses",
                modifier = Modifier,
                visible = state.success.isNotEmpty(),
                onDismiss = {
                    onEvent(SearchCustomerEvent.ShowSuccessAlert(false))
                }
            )
            MessageAlert(
                type = AlertType.ERROR,
                message = state.error,
                title = "Error",
                modifier = Modifier,
                visible = state.error.isNotEmpty(),
                onDismiss = {
                    onEvent(SearchCustomerEvent.ShowErrorAlert(false))
                }
            )
        }
    }
}

@Preview
@Composable
private fun SearchCustomerPreview() {
    VcashierMobileTheme {
        Surface {
//            SearchCustomerScreenNew()
        }
    }
}