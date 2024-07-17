package com.vixiloc.vcashiermobile.presentation.screens.product_log.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.TextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.screens.product_log.add_logs.AddProductLogEvent
import com.vixiloc.vcashiermobile.presentation.screens.product_log.add_logs.AddProductLogViewModel
import com.vixiloc.vcashiermobile.presentation.screens.product_log.add_logs.InputName

@Composable
fun AddLogDialog(
    modifier: Modifier = Modifier,
    viewModel: AddProductLogViewModel
) {
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent
    if (state.showAddLogDialog) {
        Dialog(
            onDismissRequest = {
                onEvent(AddProductLogEvent.ShowAddLogDialog(false))
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
                        text = "Tambah Log Produk",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight(600)
                        )
                    )

                    IconButton(onClick = {
                        onEvent(AddProductLogEvent.ShowAddLogDialog(false))
                    }, icon = Icons.Outlined.Close)
                }
                VerticalSpacer(height = 10.dp)
                TextField(
                    value = state.logInfo,
                    onValueChanged = {
                        onEvent(AddProductLogEvent.ChangeInput(InputName.Info, it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    title = "",
                    placeHolder = "Masukkan keterangan",
                    isError = state.logInfoError.isNotBlank(),
                    errorMessage = state.logInfoError
                )
                TextField(
                    value = state.logAmount,
                    onValueChanged = {
                        if (it.isDigitsOnly()) {
                            onEvent(AddProductLogEvent.ChangeInput(InputName.Amount, it))
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    title = "",
                    placeHolder = "Masukkan jumlah stok",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = state.logAmountError.isNotBlank(),
                    errorMessage = state.logAmountError
                )

                VerticalSpacer(height = 11.dp)
                Text(
                    text = "Tipe Log",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodySmall
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = state.logType == "increase",
                            onClick = {
                                onEvent(AddProductLogEvent.ChangeInput(InputName.Type, "increase"))
                            }
                        )
                        Text(text = "Tambah Stok", style = MaterialTheme.typography.bodySmall)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = state.logType == "decrease",
                            onClick = {
                                onEvent(AddProductLogEvent.ChangeInput(InputName.Type, "decrease"))
                            }
                        )
                        Text(text = "Kurangi Stok", style = MaterialTheme.typography.bodySmall)
                    }
                }
                FilledButton(
                    onClick = {
                        onEvent(AddProductLogEvent.AddProductLog)
                    },
                    text = "Tambah",
                    modifier = Modifier.fillMaxWidth(0.4f),
                    textStyle = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(600))
                )
            }
        }
    }
}