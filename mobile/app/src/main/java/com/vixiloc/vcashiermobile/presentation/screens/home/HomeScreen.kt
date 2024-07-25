package com.vixiloc.vcashiermobile.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction.CreateTransactionScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: HomeViewModel = koinViewModel()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
        Text(
            text = "Selamat datang!",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(600))
        )
        Text(
            text = viewModel.role.value.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}