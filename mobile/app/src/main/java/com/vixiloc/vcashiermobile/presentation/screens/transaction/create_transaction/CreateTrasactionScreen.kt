package com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.commons.CurrencyFormatter
import com.vixiloc.vcashiermobile.presentation.navigations.Screens
import com.vixiloc.vcashiermobile.presentation.components.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.components.commons.FloatingTransactionButton
import com.vixiloc.vcashiermobile.presentation.components.commons.Loading
import com.vixiloc.vcashiermobile.presentation.components.commons.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.commons.TextField
import com.vixiloc.vcashiermobile.presentation.components.commons.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.components.products.TransactionProductItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateTransactionScreen(
    navigator: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: CreateTransactionViewModel = koinViewModel()
) {
    val state = viewModel.state
    val onEvent = viewModel::onEvent
    LaunchedEffect(key1 = Unit) {
        onEvent(CreateTransactionEvent.ClearCart)
        onEvent(CreateTransactionEvent.Refresh)
    }
    val context = LocalContext.current


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFF6F5F5))
    ) {

        TextField(
            value = "Cari",
            onValueChanged = {},
            modifier = Modifier,
            title = "Cari",
            textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (buttonBottom, lazyVerticalGrid) = createRefs()
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.constrainAs(lazyVerticalGrid) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                items(state.products) { product ->
                    TransactionProductItem(
                        price = product.price.toString(),
                        name = "${product.product.name} - ${product.unit}",
                        image = product.product.imageUrl ?: "",
                        onAdd = {
                            onEvent(CreateTransactionEvent.SelectProduct(product))
                        },
                        showAddButton = !state.selectedProduct.any { it.containsKey(product) }
                    )
                }
                item {
                    VerticalSpacer(height = 100.dp)
                }
            }
            this@Column.AnimatedVisibility(visible = state.selectedProduct.isNotEmpty(),
                modifier = Modifier
                    .constrainAs(buttonBottom) {
                        bottom.linkTo(lazyVerticalGrid.bottom)
                        start.linkTo(lazyVerticalGrid.start)
                        end.linkTo(lazyVerticalGrid.end)
                    }) {
                FloatingTransactionButton(
                    onClick = {
                        navigator.navigate(Screens.Transactions.ReviewTransaction)
                        navigator.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("products", state.selectedProduct)
                        navigator.currentBackStackEntry?.savedStateHandle?.set(
                            "total",
                            state.totalPrice
                        )
                    },
                    modifier = Modifier,
                    containerColor = MaterialTheme.colorScheme.primary,
                    icon = Icons.Outlined.ShoppingCart,
                    textStart = "${state.selectedProduct.count()} Item",
                    textEnd = CurrencyFormatter.formatCurrency(state.totalPrice)
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
                    onEvent(CreateTransactionEvent.DismissAlertMessage)
                }
            )
            MessageAlert(
                type = AlertType.ERROR,
                message = state.error,
                title = "Error",
                modifier = Modifier,
                visible = state.error.isNotEmpty(),
                onDismiss = {
                    onEvent(CreateTransactionEvent.DismissAlertMessage)
                }
            )
        }
    }
}