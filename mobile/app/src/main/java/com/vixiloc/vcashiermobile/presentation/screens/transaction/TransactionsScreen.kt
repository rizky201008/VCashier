package com.vixiloc.vcashiermobile.presentation.screens.transaction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.commons.CurrencyFormatter
import com.vixiloc.vcashiermobile.commons.DateFormatter
import com.vixiloc.vcashiermobile.domain.model.transactions.TransactionsData
import com.vixiloc.vcashiermobile.presentation.navigations.ScreensOld
import com.vixiloc.vcashiermobile.presentation.components.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.components.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.commons.Loading
import com.vixiloc.vcashiermobile.presentation.components.commons.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.transaction.TransactionItem
import kotlinx.coroutines.launch
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
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val state = viewModel.state
    val onEvent = viewModel::onEvent

    LaunchedEffect(Unit) {
        viewModel.getTransactions()
    }

    val searchBarStatus: Boolean = state.searchStatus
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

    var selected by remember { mutableIntStateOf(0) }
    Column(
        modifier = modifier
            .fillMaxSize()

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(state = rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(modifier = Modifier.padding(horizontal = 3.dp), onClick = {
                onEvent(TransactionEvent.SearchStatusChanged(state.searchStatus.not()))
            }) {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                Text(text = "Cari Pelanggan")
            }
            Button(modifier = Modifier.padding(horizontal = 3.dp), onClick = {
                navHostController.navigate(ScreensOld.Transactions.CreateTransaction)
            }) {
                Icon(imageVector = Icons.Outlined.ShoppingCart, contentDescription = null)
                Text(text = "Buat Transaksi")
            }
        }
        AnimatedVisibility(visible = searchBarStatus) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight),
                query = query,
                onQueryChange = {
                    onEvent(TransactionEvent.SearchQueryChanged(it))
                },
                onSearch = {
                    onEvent(TransactionEvent.SearchQueryChanged(it))
                },
                placeholder = { Text(text = "Search Transaction") },
                active = searchBarStatus,
                onActiveChange = {
                    onEvent(TransactionEvent.SearchStatusChanged(it))
                },
                colors = SearchBarDefaults.colors(
                    containerColor = Color.White,
                ),
                shadowElevation = 2.dp,
            ) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(state.searchResult) { transaction: TransactionsData ->
                        TransactionItem(
                            modifier = Modifier.padding(5.dp),
                            transactionCustomer = transaction.customer?.name ?: "",
                            transactionStatus = transaction.transactionStatus,
                            transactionTotal = CurrencyFormatter.formatCurrency(transaction.totalAmount),
                            transactionDate = DateFormatter.format(
                                transaction.createdAt ?: "2024-05-21T06:14:23.000000Z"
                            ),
                            onClick = {
                                scope.launch {
                                    sheetState.show()
                                    onEvent(
                                        TransactionEvent.SelectTransaction(
                                            transaction.id
                                        )
                                    )
                                    showBottomSheet = true
                                }
                            }
                        )
                    }
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
                    transactionDate = DateFormatter.format(
                        transaction.createdAt ?: "2024-05-21T06:14:23.000000Z"
                    ),
                    onClick = {
                        scope.launch {
                            sheetState.show()
                            onEvent(
                                TransactionEvent.SelectTransaction(
                                    transaction.id
                                )
                            )
                            showBottomSheet = true
                        }
                    }
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

        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.height(screenHeight / 2f),
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    FilledButton(
                        modifier = Modifier.width(screenWidth / 2f),
                        textStyle = MaterialTheme.typography.bodySmall,
                        onClick = {

                        },
                        text = "Pembayaran"
                    )
                    FilledButton(
                        modifier = Modifier.width(screenWidth / 2f),
                        onClick = { /*TODO*/ },
                        text = "Batalkan",
                        textStyle = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}