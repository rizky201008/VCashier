package com.vixiloc.vcashiermobile.presentation.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.commons.CurrencyFormatter
import com.vixiloc.vcashiermobile.domain.model.CustomerResponseItem
import com.vixiloc.vcashiermobile.domain.model.ProductResponseItems
import com.vixiloc.vcashiermobile.presentation.navigations.Screens
import com.vixiloc.vcashiermobile.presentation.components.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.components.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.commons.FloatingTransactionButton
import com.vixiloc.vcashiermobile.presentation.components.commons.Loading
import com.vixiloc.vcashiermobile.presentation.components.commons.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.commons.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.components.transaction.CustomerItem
import com.vixiloc.vcashiermobile.presentation.components.products.TransactionHorizontalProductItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionReviewScreen(
    navigator: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: TransactionViewModel = koinViewModel(),
    customer: CustomerResponseItem?,
    products: List<Map<ProductResponseItems, Int>>,
    total: Int
) {
    val state = viewModel.state
    val onEvent = viewModel::onEvent
    LaunchedEffect(Unit) {
        onEvent(TransactionEvent.UpdateCustomer(customer))
        onEvent(TransactionEvent.InsertSelectedProducts(products))
        onEvent(TransactionEvent.InsertTransactionTotal(total))
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFF6F5F5))
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (lazyColumn, buttonBottom) = createRefs()
            LazyColumn(
                modifier = Modifier.constrainAs(lazyColumn) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                items(state.selectedProduct) {
                    it.map { data: Map.Entry<ProductResponseItems, Int> ->
                        TransactionHorizontalProductItem(
                            price = data.key.price,
                            name = "${data.key.product.name} - ${data.key.unit}",
                            image = data.key.product.imageUrl,
                            amount = data.value,
                            onAdd = {
                                viewModel.onEvent(TransactionEvent.IncreaseQty(data.key))
                            },
                            onRemove = {
                                viewModel.onEvent(TransactionEvent.DecreaseQty(data.key))
                            }
                        )
                    }
                }
                item {
                    VerticalSpacer(height = 10.dp)
                }
                item { HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp)) }
                item {
                    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text(
                            text = "Pelanggan",
                            modifier = Modifier,
                            style = MaterialTheme.typography.titleMedium
                        )
                        CustomerItem(
                            modifier = Modifier.padding(vertical = 10.dp),
                            headlineText = state.customer.name,
                            supportingText = state.customer.phoneNumber ?: "-",
                            onUpdate = { navigator.navigate(Screens.Transactions.SearchCustomer) })
                    }
                }
                item {
                    VerticalSpacer(height = 10.dp)
                }
                item {
                    VerticalSpacer(height = 100.dp)
                }
            }
            FloatingTransactionButton(
                onClick = {
                    onEvent(TransactionEvent.SubmitTransaction)
                }, modifier = Modifier
                    .constrainAs(buttonBottom) {
                        bottom.linkTo(lazyColumn.bottom)
                        start.linkTo(lazyColumn.start)
                        end.linkTo(lazyColumn.end)
                    },
                containerColor = MaterialTheme.colorScheme.primary,
                icon = null,
                textStart = "${state.selectedProduct.size} Item",
                textEnd = CurrencyFormatter.formatCurrency(state.totalPrice)
            )
            Loading(modifier = Modifier, visible = state.isLoading)

            MessageAlert(
                type = AlertType.SUCCESS,
                message = state.success,
                title = "Sukses",
                modifier = Modifier,
                visible = state.success.isNotEmpty(),
                onDismiss = {
                    onEvent(TransactionEvent.DismissAlertMessage)
                    navigator.popBackStack()
                    navigator.navigate(Screens.Transactions.AllTransactions)
                },
                confirmButton = {
                    FilledButton(
                        onClick = { /*TODO*/ },
                        text = "Lanjut Pembayaran",
                        modifier = Modifier
                    )
                },
                dismissButton = {
                    TextButton(onClick = {
                        onEvent(TransactionEvent.DismissAlertMessage)
                        navigator.popBackStack()
                        navigator.navigateUp()
                    }, modifier = Modifier.padding(vertical = 5.dp)) {
                        Text(text = "Nanti saja")
                    }
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
}