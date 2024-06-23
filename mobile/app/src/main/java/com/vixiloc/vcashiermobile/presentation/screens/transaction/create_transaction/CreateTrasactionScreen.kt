package com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.presentation.components.commons.AddToCartModal
import com.vixiloc.vcashiermobile.presentation.components.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.components.commons.Loading
import com.vixiloc.vcashiermobile.presentation.components.commons.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.commons.SearchTextField
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
            .padding(horizontal = 12.dp)
    ) {
        VerticalSpacer(height = 24.dp)
        SearchTextField(
            value = "Cari",
            onValueChanged = {},
            modifier = Modifier,
            textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )
        VerticalSpacer(height = 37.dp)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.products) { product ->
                TransactionProductItem(
                    price = product.price.toString(),
                    name = "${product.product.name} - ${product.unit}",
                    image = product.product.imageUrl ?: "",
                    onAdd = {
                        onEvent(CreateTransactionEvent.SelectProduct(product))
                    },
                    modifier = Modifier
                )
            }
            item {
                VerticalSpacer(height = 100.dp)
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
        AddToCartModal()
    }
}