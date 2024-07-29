package com.vixiloc.vcashiermobile.presentation.screens.transaction.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.screens.transaction.report.component.TransactionListItem
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(modifier: Modifier = Modifier, navHostController: NavHostController) {
    val viewModel: ReportViewModel = koinViewModel()
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                title = {
                    Text(
                        text = "Laporan penjualan",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight(600)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navHostController.navigateUp()
                        },
                        icon = Icons.Outlined.ArrowBackIosNew
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .verticalScroll(state = rememberScrollState())
                .padding(25.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = MaterialTheme.shapes.medium)
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column {
                        Text(text = "Omset hari ini", style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = state.data?.todayOmzet ?: "0",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Column {
                        Text(text = "Profit hari ini", style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = state.data?.todayProfit ?: "0",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF45B004)
                            )
                        )
                    }
                }
            }
            VerticalSpacer(height = 24.dp)
            Text(
                text = "Transaksi terbaru",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            VerticalSpacer(height = 12.dp)
            LazyColumn(modifier = Modifier.height(600.dp)) {
                item {
                    Loading(visible = state.isLoading)
                }
                items(state.data?.transactions ?: emptyList()) { data ->
                    TransactionListItem(data = data)
                    VerticalSpacer(height = 6.dp)
                }
                item {
                    VerticalSpacer(height = 150.dp)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ReportScreenPreview() {
    VcashierMobileTheme {
        Surface {
//            ReportScreen(navHostController = navHostController)
        }
    }
}