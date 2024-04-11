package com.vixiloc.vcashiermobile.presentation.screens.transaction

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vixiloc.vcashiermobile.R
import com.vixiloc.vcashiermobile.presentation.widgets.utils.FilledButton
import com.vixiloc.vcashiermobile.presentation.widgets.utils.OutlinedButton
import com.vixiloc.vcashiermobile.presentation.widgets.utils.VerticalSpacer

@Composable
fun TransactionSuccessScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        Color(0xFF000151)
                    )
                )
            )
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Transaksi berhasil dibuat",
            style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
        )
        VerticalSpacer(height = 64.dp)
        Image(
            painter = painterResource(id = R.drawable.success_icon),
            contentDescription = null
        )
        VerticalSpacer(height = 100.dp)
        OutlinedButton(
            onClick = { /*TODO*/ },
            text = "Print",
            modifier = Modifier.padding(horizontal = 10.dp),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
        )
        VerticalSpacer(height = 19.dp)
        FilledButton(
            onClick = { /*TODO*/ },
            text = "Beranda",
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}