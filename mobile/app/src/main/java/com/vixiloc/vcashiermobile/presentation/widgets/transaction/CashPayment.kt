package com.vixiloc.vcashiermobile.presentation.widgets.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vixiloc.vcashiermobile.presentation.widgets.utils.TextField
import com.vixiloc.vcashiermobile.presentation.widgets.utils.VerticalSpacer

@Composable
fun CashPayment() {
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = "",
            onValueChanged = {},
            modifier = Modifier,
            title = "Pembayaran Pembeli",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        VerticalSpacer(height = 30.dp)
        Text(
            text = "Kembalian",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Text(
            text = "Rp0",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}