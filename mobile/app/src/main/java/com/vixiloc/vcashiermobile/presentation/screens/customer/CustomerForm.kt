package com.vixiloc.vcashiermobile.presentation.screens.customer

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.TextField

enum class FormType {
    CREATE, UPDATE
}

@Composable
fun CustomerForm(modifier: Modifier = Modifier, viewModel: CustomerViewModel, type: FormType) {
    val state = viewModel.state
    val events = viewModel::onEvent
    TextField(
        value = state.customerName,
        onValueChanged = { events(CustomerEvent.InputCustomerName(it)) },
        modifier = Modifier,
        title = "Nama Customer"
    )

    TextField(
        value = state.customerNumber ?: "",
        onValueChanged = { events(CustomerEvent.InputCustomerNumber(it)) },
        modifier = Modifier,
        title = "No. Telp",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    FilledButton(
        onClick = {
            if (type == FormType.CREATE) {
                events(CustomerEvent.SubmitCreateCustomer)
            } else {
                events(CustomerEvent.SubmitUpdateCustomer)
            }
        },
        text = "Submit",
        modifier = Modifier.padding(horizontal = 10.dp)
    )
}