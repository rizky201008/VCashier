package com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.FloatingTransactionButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.SearchTextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.products.components.TransactionProductItem
import com.vixiloc.vcashiermobile.presentation.screens.transaction.components.AddToCartModal
import com.vixiloc.vcashiermobile.utils.CurrencyFormatter
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateTransactionScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateTransactionViewModel = koinViewModel(),
    onNavigate: (MainRoutes) -> Unit
) {
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    var menuExpanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {
        Row(
            Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledButton(
                onClick = {
                    menuExpanded = !menuExpanded
                },
                text = state.selectedCategory?.name ?: "Semua Kategori",
                modifier = Modifier,
                trailingIcon = Icons.Outlined.KeyboardArrowDown
            )
            DropdownMenu(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .width(screenWidth / 2f),
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
            ) {
                DropdownMenuItem(
                    text = { Text("Semua Kategori") },
                    onClick = {
                        onEvent(CreateTransactionEvent.SelectCategory(null))
                        menuExpanded = false
                    }
                )
                state.categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        onClick = {
                            onEvent(CreateTransactionEvent.SelectCategory(category))
                            menuExpanded = false
                        }
                    )
                }
            }

            IconButton(onClick = { /*TODO*/ }) {
                FaIcon(faIcon = FaIcons.Filter, size = 24.dp)
            }
        }
        VerticalSpacer(height = 24.dp)
        SearchTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.searchQuery,
            onValueChanged = {
                onEvent(CreateTransactionEvent.UpdateSearchValue(it))
            },
            textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
        VerticalSpacer(height = 37.dp)
        Loading(modifier = Modifier, visible = state.isLoading)
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (products, cartInfo) = createRefs()
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .constrainAs(products) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                        height = Dimension.matchParent
                    },
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.products) { product ->
                    TransactionProductItem(
                        name = product.name,
                        image = product.imageUrl ?: "",
                        onAdd = {
                            onEvent(CreateTransactionEvent.AddVariation(it, product))
                        },
                        modifier = Modifier,
                        variations = product.variations
                    )
                }
                item {
                    VerticalSpacer(height = 100.dp)
                }
                item {
                    VerticalSpacer(height = 100.dp)
                }
            }

            FloatingTransactionButton(
                onClick = {
                    onNavigate(MainRoutes.NavDrawerScreens.Transactions.Checkout)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(cartInfo) {
                        start.linkTo(products.start)
                        end.linkTo(products.end)
                        bottom.linkTo(products.bottom, margin = 24.dp)
                    },
                textStart = "${state.cartItems.count()} Item",
                textEnd = CurrencyFormatter.formatCurrency(state.cartItems.sumOf { it.price }),
                icon = Icons.Outlined.ShoppingCart
            )
        }

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

        state.selectedVariation?.let {
            if (state.selectedProduct != null) {
                AnimatedVisibility(visible = true) {
                    AddToCartModal(
                        variation = it,
                        onDismissRequest = {
                            onEvent(CreateTransactionEvent.DismissAddToCartModal)
                        },
                        product = state.selectedProduct,
                        onConfirm = {
                            onEvent(CreateTransactionEvent.AddToCart(it))
                        }
                    )
                }
            }
        }
    }
}