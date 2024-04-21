package com.vixiloc.vcashiermobile.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vixiloc.vcashiermobile.presentation.screens.destinations.CreateTransactionScreenDestination
import com.vixiloc.vcashiermobile.presentation.widgets.commons.FilledButton

@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFF6F5F5))
    ) {
        Text(text = "Halo Ges")
        FilledButton(onClick = { navigator.navigate(CreateTransactionScreenDestination) }, text = "Buat Transaksi", modifier = Modifier)
    }
}