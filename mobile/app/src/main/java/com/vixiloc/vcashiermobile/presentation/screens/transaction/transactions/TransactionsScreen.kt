package com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.vixiloc.vcashiermobile.domain.model.transactions.TransactionsCustomer
import com.vixiloc.vcashiermobile.utils.CurrencyFormatter
import com.vixiloc.vcashiermobile.utils.DateFormatter
import com.vixiloc.vcashiermobile.domain.model.transactions.TransactionsData
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.HorizontalSpacer
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.SearchTextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.category.CategoryEvent
import com.vixiloc.vcashiermobile.presentation.screens.category.components.FilledIconButton
import com.vixiloc.vcashiermobile.presentation.screens.transaction.components.TransactionItem
import com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions.components.TransactionListItem
import com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions.components.TransactionStatusChip
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreenOld(
    modifier: Modifier = Modifier,
    viewModel: TransactionViewModel = koinViewModel(),
    onNavigate: (MainRoutes) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val state = viewModel.state
    val onEvent = viewModel::onEvent
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

@Composable
fun TransactionsScreen(modifier: Modifier = Modifier) {
    val viewModel: TransactionViewModel = koinViewModel()
    val state = viewModel.state
    val onEvent = viewModel::onEvent
    val focusManager = LocalFocusManager.current
    val status: List<String> = listOf(
        "semua",
        "pending",
        "shipping",
        "canceled",
        "completed",
        "draft"
    )
    var selectedStatus by remember { mutableIntStateOf(0) }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        val (content, bottomButton) = createRefs()
        Column(
            modifier = Modifier.constrainAs(content) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(state = rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HorizontalSpacer(width = 8.dp)
                status.forEachIndexed { index, status ->
                    TransactionStatusChip(
                        modifier = Modifier,
                        name = status.replaceFirstChar { it.uppercase() },
                        enabled = selectedStatus == index,
                        onClick = {
                            onEvent(TransactionEvent.SelectStatus(status))
                            selectedStatus = index
                        }
                    )
                }
                HorizontalSpacer(width = 8.dp)
            }
            VerticalSpacer(height = 16.dp)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SearchTextField(
                        modifier = Modifier.width(242.dp),
                        value = "",
                        onValueChanged = {

                        },
                        placeHolder = "Cari pelanggan",
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    )
                    FilledIconButton(
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.medium
                        ),
                        icon = Icons.Outlined.FilterAlt, onClick = {}
                    )
                }

                VerticalSpacer(height = 32.dp)

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(state.transactions) { transaction: TransactionsData ->
                        TransactionListItem(
                            data = transaction,
                            onClick = {}
                        )
                        VerticalSpacer(height = 12.dp)
                    }
                    item {
                        VerticalSpacer(height = 150.dp)
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .constrainAs(bottomButton) {
                    width = Dimension.matchParent
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .background(color = Color.White)
                .padding(horizontal = 24.dp, vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            FilledButton(
                onClick = {

                },
                text = "Buat Transaksi",
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(700)),
                contentPadding = PaddingValues(15.dp)
            )
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

@Preview
@Composable
private fun TransactionsScreenPreview() {
    VcashierMobileTheme {
        Surface {
            TransactionsScreen()
        }
    }
}