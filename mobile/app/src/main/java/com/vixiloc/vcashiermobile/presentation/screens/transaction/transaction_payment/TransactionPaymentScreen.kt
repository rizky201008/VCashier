package com.vixiloc.vcashiermobile.presentation.screens.transaction.transaction_payment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.presentation.navigations.Screens
import com.vixiloc.vcashiermobile.presentation.screens.transaction.TransactionEvent
import com.vixiloc.vcashiermobile.presentation.screens.transaction.TransactionViewModel
import com.vixiloc.vcashiermobile.presentation.widgets.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.widgets.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.widgets.commons.Loading
import com.vixiloc.vcashiermobile.presentation.widgets.commons.MessageAlert
import com.vixiloc.vcashiermobile.presentation.widgets.commons.TextField
import com.vixiloc.vcashiermobile.presentation.widgets.commons.VerticalSpacer
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionPaymentScreen(
    navigator: NavHostController,
    navArgs: Screens.Transactions.MakePayment,
    modifier: Modifier,
    viewModel: TransactionViewModel = koinViewModel()
) {
    val onEvent = viewModel::onEvent
    val state = viewModel.state
    LaunchedEffect(Unit) {
        viewModel.getTransaction(navArgs.transactionId)
        viewModel.getPaymentMethods()
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Column(
        modifier = modifier.padding(horizontal = 10.dp)
    ) {
        TextField(
            modifier = Modifier,
            value = state.paymentAmount.toString(),
            onValueChanged = {
                onEvent(TransactionEvent.UpdatePaymentAmount(it))
            },
            title = "Nominal Pembayaran",
            textStyle = MaterialTheme.typography.titleMedium.copy(color = Color.Black),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = state.paymentAmount == state.transactionTotal,
                onCheckedChange = {
                    if (it) {
                        onEvent(TransactionEvent.UpdatePaymentAmount((state.transactionTotal).toString()))
                    }
                })
            Text(
                text = "Uang Pas",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
        }

        TextField(
            value = (state.transactionTotal - state.paymentAmount).toString(),
            onValueChanged = {},
            modifier = Modifier.fillMaxWidth(),
            title = "Kembalian",
            enabled = false,
            textStyle = MaterialTheme.typography.titleMedium.copy(color = Color.Black),
        )

        VerticalSpacer(height = 10.dp)

        state.paymentMethods.forEach { paymentMethod ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (state.paymentMethod == paymentMethod),
                        onClick = {
                            onEvent(TransactionEvent.SelectPaymentMethod(paymentMethod))
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (state.paymentMethod == paymentMethod),
                    onClick = null
                )
                Text(
                    text = "${paymentMethod.name} - Biaya Rp${paymentMethod.fee}",
                    style = MaterialTheme.typography.bodySmall.merge(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        FilledButton(
            onClick = { /*TODO*/ },
            text = "Bayar Sekarang",
            modifier = Modifier.fillMaxWidth()
        )

        Loading(modifier = Modifier, visible = state.isLoading)

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
    }
}