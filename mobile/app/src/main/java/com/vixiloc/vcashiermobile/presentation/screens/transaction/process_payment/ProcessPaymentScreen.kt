package com.vixiloc.vcashiermobile.presentation.screens.transaction.process_payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.TextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.transaction.components.PaymentMethodItem
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionPaymentScreen(
    modifier: Modifier = Modifier,
    navigator: NavHostController,
    navArgs: MainRoutes.NavDrawerScreens.Transactions.MakePayment,
) {
    val viewModel: ProcessPaymentViewModel = koinViewModel()
    val onEvent = viewModel::onEvent
    val state = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.getTransaction(navArgs.transactionId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                title = {
                    Text(
                        text = "Pembayaran",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight(600)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigator.navigateUp()
                        },
                        icon = Icons.Outlined.ArrowBackIosNew
                    )
                }
            )
        }
    ) { padding ->
        ConstraintLayout(
            modifier = Modifier.padding(padding)
        ) {
            val (content, button) = createRefs()
            Column(
                modifier = modifier
                    .padding(horizontal = 24.dp)
                    .constrainAs(content) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.matchParent
                    }
            ) {
                TextField(
                    modifier = Modifier,
                    value = state.paymentAmount.toString(),
                    onValueChanged = {
                        onEvent(ProcessPaymentEvent.UpdatePaymentAmount(it))
                    },
                    title = "Nominal Pembayaran",
                    textStyle = MaterialTheme.typography.titleMedium.copy(color = Color.Black),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        modifier = Modifier,
                        checked = state.paymentAmount == state.transactionTotal,
                        onCheckedChange = {
                            if (it) {
                                onEvent(ProcessPaymentEvent.UpdatePaymentAmount((state.transactionTotal).toString()))
                            }
                        })
                    Text(
                        text = "Uang Pas",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight(500)
                        )
                    )
                }

                TextField(
                    value = if (state.paymentAmount > state.transactionTotal) (state.paymentAmount - state.transactionTotal).toString() else "0",
                    onValueChanged = {},
                    modifier = Modifier.fillMaxWidth(),
                    title = "Kembalian",
                    enabled = false,
                    textStyle = MaterialTheme.typography.titleMedium.copy(color = Color.Black),
                )

                VerticalSpacer(height = 32.dp)

                Text(
                    text = "Metode Pembayaran",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight(600)
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                LazyColumn {
                    items(state.paymentMethods) { paymentMethod ->
                        PaymentMethodItem(
                            onClick = {
                                onEvent(ProcessPaymentEvent.SelectPaymentMethod(paymentMethod))
                            },
                            paymentMethods = paymentMethod,
                            selected = state.paymentMethod == paymentMethod
                        )
                    }
                }

                Loading(modifier = Modifier, visible = state.isLoading)
            }

            Box(
                modifier = Modifier
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.matchParent
                    }
                    .background(color = Color.White)
                    .padding(horizontal = 24.dp, vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                FilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onEvent(ProcessPaymentEvent.SubmitTransactionPayment)
                    },
                    text = "Bayar Sekarang",
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight(600))
                )
            }

            MessageAlert(
                type = AlertType.SUCCESS,
                message = state.success,
                title = "Sukses",
                modifier = Modifier,
                visible = state.success.isNotEmpty(),
                onDismiss = {
                    onEvent(ProcessPaymentEvent.DismissAlertMessage)
                    navigator.popBackStack()
                    navigator.navigate(
                        MainRoutes.NavDrawerScreens.Transactions.PayTransaction(
                            navArgs.transactionId
                        )
                    )
                }
            )
            MessageAlert(
                type = AlertType.ERROR,
                message = state.error,
                title = "Error",
                modifier = Modifier,
                visible = state.error.isNotEmpty(),
                onDismiss = {
                    onEvent(ProcessPaymentEvent.DismissAlertMessage)
                }
            )
        }
    }
}

@Preview
@Composable
private fun TransactionPaymentScreenPreview() {
    VcashierMobileTheme {
        Surface {

        }
    }
}