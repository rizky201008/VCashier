package com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions.TransactionEvent
import com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions.TransactionViewModel

@Composable
fun TransactionActionDialog(
    modifier: Modifier = Modifier,
    viewModel: TransactionViewModel,
    onPay: (String) -> Unit,
    onProcessPayment: (String) -> Unit
) {
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Dialog(
        onDismissRequest = {
            onEvent(TransactionEvent.ShowTransactionAction(false, null))
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium
                )
                .verticalScroll(state = rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Ubah transaksi",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight(600)
                    )
                )

                IconButton(onClick = {
                    onEvent(TransactionEvent.ShowTransactionAction(false, null))
                }, icon = Icons.Outlined.Close)
            }

            if (state.selectedTransaction!!.transactionStatus != "canceled") {
                FilledButton(
                    onClick = {
                        onEvent(TransactionEvent.CancelTransaction(state.selectedTransaction!!))
                    },
                    text = "Batalkan",
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                )
            } else {
                Text(
                    text = "Transaksi anda sudah dibatalkan dan anda tidak dapat mengubahnya lagi.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (state.selectedTransaction.paymentStatus == "unpaid") {
                FilledButton(
                    onClick = {
                        onPay(state.selectedTransaction.id)
                    },
                    text = "Bayar",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (state.selectedTransaction.transactionStatus == "draft") {
                FilledButton(
                    onClick = {
                        onProcessPayment(state.selectedTransaction.id)
                    },
                    text = "Proses Pembayaran",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}