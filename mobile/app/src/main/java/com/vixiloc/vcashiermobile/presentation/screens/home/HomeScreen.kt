package com.vixiloc.vcashiermobile.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction.CreateTransactionScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: (MainRoutes) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        CreateTransactionScreen(onNavigate = onNavigate)
    }
}