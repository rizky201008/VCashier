package com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.vixiloc.vcashiermobile.domain.model.transactions.TransactionsData
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.HorizontalSpacer
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.SearchTextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.transaction.components.TransactionListItem
import com.vixiloc.vcashiermobile.presentation.screens.transaction.components.TransactionStatusChip
import com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions.components.TransactionActionDialog
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onNavigate: (MainRoutes) -> Unit,
    onTitleChange: (String) -> Unit
) {
    val viewModel: TransactionViewModel = koinViewModel()
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent
    val focusManager = LocalFocusManager.current
    val status: List<String> = listOf(
        "semua",
        "pending",
        "canceled",
        "completed",
        "draft"
    )

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        val (content, bottomButton) = createRefs()
        Column(
            modifier = Modifier.constrainAs(content) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(state = rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HorizontalSpacer(width = 8.dp)
                status.forEachIndexed { index, status ->
                    TransactionStatusChip(
                        modifier = Modifier,
                        name = status.replaceFirstChar { it.uppercase() },
                        enabled = state.status == status,
                        onClick = {
                            onEvent(TransactionEvent.SelectStatus(status))
                        }
                    )
                }
                HorizontalSpacer(width = 8.dp)
            }
            VerticalSpacer(height = 16.dp)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
            ) {
                SearchTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.searchQuery,
                    onValueChanged = {
                        onEvent(TransactionEvent.OnSearchChanged(it))
                    },
                    placeHolder = "Cari pelanggan",
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                )

                VerticalSpacer(height = 32.dp)

                Loading(modifier = Modifier, visible = state.isLoading)

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(state.transactions) { transaction: TransactionsData ->
                        TransactionListItem(
                            data = transaction,
                            onClick = {
                                onEvent(
                                    TransactionEvent.ShowTransactionAction(
                                        true,
                                        transaction
                                    )
                                )
                            }
                        )
                        VerticalSpacer(height = 12.dp)
                    }
                    item {
                        VerticalSpacer(height = 150.dp)
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .constrainAs(bottomButton) {
                    width = Dimension.matchParent
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .background(color = Color.White)
                .padding(horizontal = 24.dp, vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            FilledButton(
                onClick = {
                    navController.navigate(MainRoutes.NavDrawerScreens.Home)
                    onTitleChange("Home")
                },
                text = "Buat Transaksi",
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(700)),
                contentPadding = PaddingValues(15.dp)
            )
        }

        MessageAlert(
            type = AlertType.SUCCESS,
            message = state.success,
            title = "Sukses",
            modifier = Modifier,
            visible = state.success.isNotEmpty(),
            onDismiss = {
                onEvent(TransactionEvent.DismissAlertMessage)
            }
        )
        MessageAlert(
            type = AlertType.ERROR,
            message = state.error,
            title = "Error",
            modifier = Modifier,
            visible = state.error.isNotEmpty(),
            onDismiss = {
                onEvent(TransactionEvent.DismissAlertMessage)
            }
        )

        if (state.showTransactionAction) {
            TransactionActionDialog(
                viewModel = viewModel,
                onPay = {
                    onNavigate(MainRoutes.NavDrawerScreens.Transactions.PayTransaction(it))
                },
                onProcessPayment = {
                    onNavigate(MainRoutes.NavDrawerScreens.Transactions.MakePayment(it))
                }
            )
        }
    }
}

@Preview
@Composable
private fun TransactionsScreenPreview() {
    VcashierMobileTheme {
        Surface {
//            TransactionsScreen()
        }
    }
}