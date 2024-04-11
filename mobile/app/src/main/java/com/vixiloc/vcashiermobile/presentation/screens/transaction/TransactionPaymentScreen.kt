package com.vixiloc.vcashiermobile.presentation.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vixiloc.vcashiermobile.presentation.widgets.transaction.CashPayment
import com.vixiloc.vcashiermobile.presentation.widgets.transaction.CashlessPayment
import com.vixiloc.vcashiermobile.presentation.widgets.utils.IconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionPaymentScreen() {
    var tabState by remember { mutableStateOf(0) }
    val titles = listOf("Tunai", "Non Tunai")
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Pembayaran",
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
                )
            }, navigationIcon = {
                IconButton(onClick = { /*TODO*/ }, icon = Icons.Outlined.ArrowBackIosNew)
            })
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    text = "Konfirmasi",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = Color(0xFFF6F5F5))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val textModifier = Modifier.padding(10.dp)
                Text(
                    text = "Total Pembayaran",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = textModifier
                )
                Text(
                    text = "Rp200.000",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = textModifier
                )
            }
            PrimaryTabRow(selectedTabIndex = tabState, containerColor = Color.White) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabState == index,
                        onClick = { tabState = index },
                        text = {
                            Text(
                                text = title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                    )
                }
            }
            when (tabState) {
                0 -> {
                    CashPayment()
                }

                1 -> {
                    CashlessPayment()
                }
            }
        }
    }
}