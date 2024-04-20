package com.vixiloc.vcashiermobile.presentation.widgets.transaction

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vixiloc.vcashiermobile.presentation.widgets.commons.VerticalSpacer

@Composable
fun CashlessPayment(modifier: Modifier) {
    var active by remember {
        mutableStateOf(false)
    }
    LazyColumn(modifier = modifier) {
        items(5) {
            PaymentMethodItem(label = "Transfer Bank $it", onCLick = {
                active = !active
            }, active = active)
        }
        item {
            VerticalSpacer(height = 100.dp)
        }
    }
}