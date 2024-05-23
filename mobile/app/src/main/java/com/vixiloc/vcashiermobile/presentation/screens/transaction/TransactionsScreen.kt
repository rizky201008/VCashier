package com.vixiloc.vcashiermobile.presentation.screens.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.commons.CurrencyFormatter
import com.vixiloc.vcashiermobile.commons.DateFormatter
import com.vixiloc.vcashiermobile.domain.model.TransactionsData
import com.vixiloc.vcashiermobile.presentation.widgets.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.widgets.commons.Loading
import com.vixiloc.vcashiermobile.presentation.widgets.commons.MessageAlert
import com.vixiloc.vcashiermobile.presentation.widgets.transaction.TransactionItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: TransactionViewModel = koinViewModel()
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val state = viewModel.state
    val onEvent = viewModel::onEvent

    LaunchedEffect(Unit) {
        viewModel.getTransactions()
    }

    val searchStatus: Boolean = state.searchStatus
    val query = state.searchQuery
    val transactionLists = state.transactions

    val tabs: List<Map<String, String>> = listOf(
        mapOf("Semua" to "all"),
        mapOf("Pending" to "pending"),
        mapOf("Shipping" to "shipping"),
        mapOf("Canceled" to "canceled"),
        mapOf("Completed" to "completed"),
        mapOf("Draft" to "draft")
    )

    var selected by remember { mutableStateOf(0) }
    Column(
        modifier = modifier
            .fillMaxSize()

    ) {
        SearchBar(
            modifier = Modifier
                .padding(horizontal = if (searchStatus) 0.dp else 10.dp)
                .fillMaxWidth(),
            query = query,
            onQueryChange = {
                onEvent(TransactionEvent.SearchQueryChanged(it))
            },
            onSearch = {},
            placeholder = { Text(text = "Search Transaction") },
            active = searchStatus,
            onActiveChange = {
                onEvent(TransactionEvent.SearchStatusChanged(it))
            },
            colors = SearchBarDefaults.colors(
                containerColor = Color.White,
            ),
            shadowElevation = 2.dp) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.searchResult) { transaction: TransactionsData ->
                    TransactionItem(
                        modifier = Modifier.padding(5.dp),
                        transactionCustomer = transaction.customer?.name ?: "",
                        transactionStatus = transaction.transactionStatus,
                        transactionTotal = CurrencyFormatter.formatCurrency(transaction.totalAmount),
                        transactionDate = DateFormatter.format("2024-05-21T06:14:23.000000Z")
                    )
                }
            }
        }
        ScrollableTabRow(
            modifier = Modifier,
            selectedTabIndex = selected,
            containerColor = Color.White,
            tabs = {
                tabs.forEachIndexed { index, data ->
                    Tab(
                        modifier = Modifier.padding(10.dp),
                        selected = if (selected == index) true else false,
                        onClick = {
                            selected = index
                        }) {
                        data.map {
                            Text(text = it.key, style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
            })
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(transactionLists) { transaction: TransactionsData ->
                TransactionItem(
                    modifier = Modifier.padding(5.dp),
                    transactionCustomer = transaction.customer?.name ?: "",
                    transactionStatus = transaction.transactionStatus,
                    transactionTotal = CurrencyFormatter.formatCurrency(transaction.totalAmount),
                    transactionDate = DateFormatter.format("2024-05-21T06:14:23.000000Z")
                )
            }
        }
        Loading(modifier = Modifier, visible = state.isLoading)

        MessageAlert(
            type = AlertType.SUCCESS,
            message = state.success,
            title = "Sukses",
            modifier = Modifier,
            visible = state.success.isNotEmpty(),
            onDismiss = {
                onEvent(TransactionEvent.DismissAlertMessage)
            }
        )
        MessageAlert(
            type = AlertType.ERROR,
            message = state.error,
            title = "Error",
            modifier = Modifier,
            visible = state.error.isNotEmpty(),
            onDismiss = {
                onEvent(TransactionEvent.DismissAlertMessage)
            }
        )
    }
}