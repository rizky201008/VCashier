package com.vixiloc.vcashiermobile.presentation.screens.customer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.TextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.screens.customer.CustomerEvent
import com.vixiloc.vcashiermobile.presentation.screens.customer.CustomerFormInput
import com.vixiloc.vcashiermobile.presentation.screens.customer.CustomerViewModel

enum class InputType {
    CREATE, UPDATE
}

@Composable
fun CreateUpdateCustomerDialog(
    modifier: Modifier = Modifier,
    type: InputType,
    viewModel: CustomerViewModel
) {
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent

    Dialog(
        onDismissRequest = {
            onEvent(CustomerEvent.ShowCreateModal(false))
            onEvent(CustomerEvent.ShowUpdateModal(false))
            onEvent(CustomerEvent.FillFormData(null))
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
                    text = "Tambah Pelanggan",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight(600)
                    )
                )

                IconButton(onClick = {
                    onEvent(CustomerEvent.ShowCreateModal(false))
                    onEvent(CustomerEvent.ShowUpdateModal(false))
                    onEvent(CustomerEvent.FillFormData(null))
                }, icon = Icons.Outlined.Close)
            }
            VerticalSpacer(height = 10.dp)
            TextField(
                value = state.customerName,
                onValueChanged = {
                    onEvent(CustomerEvent.ChangeInput(CustomerFormInput.NAME, it))
                },
                modifier = Modifier.fillMaxWidth(),
                title = "",
                placeHolder = "Masukkan Nama Pelanggan",
                isError = state.customerNameError.isNotBlank(),
                errorMessage = state.customerNameError
            )
            VerticalSpacer(height = 10.dp)
            TextField(
                value = state.customerNumber,
                onValueChanged = {
                    onEvent(CustomerEvent.ChangeInput(CustomerFormInput.NUMBER, it))
                },
                modifier = Modifier.fillMaxWidth(),
                title = "",
                placeHolder = "Masukkan Nomor Telepon",
            )
            VerticalSpacer(height = 32.dp)
            FilledButton(
                onClick = {
                    if (type == InputType.CREATE) {
                        onEvent(CustomerEvent.CreateCustomer)
                    } else {
                        onEvent(CustomerEvent.UpdateCustomer)
                    }
                },
                text = if (type == InputType.CREATE) "Tambah" else "Ubah",
                modifier = Modifier.fillMaxWidth(0.4f),
                textStyle = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(600))
            )
        }
    }
}