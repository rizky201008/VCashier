package com.vixiloc.vcashiermobile.presentation.screens.transaction.pay_transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.utils.CurrencyFormatter
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.navigations.CheckoutScreens
import org.koin.androidx.compose.koinViewModel

@Composable
fun PayTransactionScreen(
    modifier: Modifier = Modifier,
    navigator: NavHostController,
    navArgs: CheckoutScreens.PayTransaction
) {
    val id = navArgs.id
    val viewModel: PayTransactionViewModel = koinViewModel()
    val state = viewModel.state.value
    val onEvent = viewModel::oneEvent
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp

    LaunchedEffect(Unit) {
        onEvent(PayTransactionEvent.GetTransaction(id))
    }

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Total Pembayaran",
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        )
        Text(
            text = CurrencyFormatter.formatCurrency(state.transactionData?.totalAmount ?: 0),
            style = MaterialTheme.typography.titleSmall.copy(textAlign = TextAlign.Start),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        )
        HorizontalDivider(modifier = Modifier.padding(bottom = 10.dp))
        Text(
            text = "Bank: ${state.transactionData?.paymentMethodId}",
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center),
            modifier = Modifier
                .fillMaxWidth()
        )
        VerticalSpacer(height = 5.dp)
        Row(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.small
                )
                .border(width = 1.dp, shape = MaterialTheme.shapes.small, color = Color.Gray)
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = state.transactionData?.vaNumber ?: "",
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.width(screenWidth / 1.5f)
            )

            IconButton(onClick = { /*TODO*/ }, icon = Icons.Default.ContentCopy, filled = true)
        }

        VerticalSpacer(height = 10.dp)

        Text(text = "Tata cara pembayaran", style = MaterialTheme.typography.titleMedium)
        Text(
            text = "Silahkan transfer ke rekening tersebut dengan bank yang sesuai dengan yang anda pilih. Transfer dengan nominal yang sesuai dengan total pembayaran diatas. Status pembayaran akan secara otomatis berubah jika anda selesai melakukan pembayaran.",
            style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Justify),
            modifier = Modifier.fillMaxWidth()
        )
        VerticalSpacer(height = 10.dp)
        FilledButton(
            onClick = { /*TODO*/ },
            text = "Cek Status",
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
                onEvent(PayTransactionEvent.DismissAlertMessage)
            }
        )
        MessageAlert(
            type = AlertType.ERROR,
            message = state.error,
            title = "Error",
            modifier = Modifier,
            visible = state.error.isNotEmpty(),
            onDismiss = {
                onEvent(PayTransactionEvent.DismissAlertMessage)
            }
        )
    }
}