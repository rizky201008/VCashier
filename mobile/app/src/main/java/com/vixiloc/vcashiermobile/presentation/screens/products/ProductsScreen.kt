package com.vixiloc.vcashiermobile.presentation.screens.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.vixiloc.vcashiermobile.commons.CurrencyFormatter
import com.vixiloc.vcashiermobile.domain.model.products.ProductResponseItems
import com.vixiloc.vcashiermobile.presentation.navigations.Screens
import com.vixiloc.vcashiermobile.presentation.components.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.components.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.commons.Loading
import com.vixiloc.vcashiermobile.presentation.components.commons.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.commons.TextField
import com.vixiloc.vcashiermobile.presentation.components.commons.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.components.products.ProductItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductsViewModel = koinViewModel(),
    navController: NavController
) {
    val state = viewModel.state
    val events = viewModel::onEvent
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.processGetProducts()
    }

    ConstraintLayout(modifier = modifier) {
        val (searchInput, categories, addButton) = createRefs()

        TextField(
            value = "",
            onValueChanged = {

            },
            modifier = Modifier.constrainAs(searchInput) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            title = "Cari",
            textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )

        Column(
            modifier = Modifier
                .constrainAs(categories) {
                    top.linkTo(searchInput.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true),
                columns = GridCells.Fixed(2)
            ) {
                items(state.products) { product: ProductResponseItems ->
                    ProductItem(
                        price = CurrencyFormatter.formatCurrency(product.price),
                        context = context,
                        name = "${product.product.name} - ${product.unit}",
                        category = product.product.category.name,
                        image = product.product.imageUrl,
                        onClick = {
                            navController.navigate(Screens.Products.UpdateProduct(product.product.id.toString()))
                        }
                    )
                }
                item { VerticalSpacer(height = 200.dp, modifier = Modifier) }
                item { VerticalSpacer(height = 200.dp, modifier = Modifier) }
            }
        }

        FilledButton(
            onClick = {
                navController.navigate(Screens.Products.CreateProduct)
            },
            text = "Tambah Produk",
            modifier = Modifier.constrainAs(addButton) {
                bottom.linkTo(parent.bottom)
                start.linkTo(categories.start, margin = 10.dp)
                end.linkTo(categories.end, margin = 10.dp)
                width = Dimension.matchParent
            }
        )

        Loading(modifier = Modifier, visible = state.isLoading)

        MessageAlert(
            type = AlertType.ERROR,
            message = state.error,
            title = "Error",
            modifier = Modifier,
            visible = state.error.isNotBlank(),
            onDismiss = { events(ProductEvent.DismissAlertMessage) }
        )

        MessageAlert(
            type = AlertType.SUCCESS,
            message = state.success,
            title = "Success",
            modifier = Modifier,
            visible = state.success.isNotBlank(),
            onDismiss = { events(ProductEvent.DismissAlertMessage) }
        )

//        MessageAlert(
//            type = AlertType.WARNING,
//            message = state.confirmationMessage,
//            title = "Hapus Kategori",
//            visible = state.confirmationMessage.isNotBlank(),
//            modifier = Modifier,
//            confirmButton = {
//                FilledButton(
//                    onClick = { events(ProductEvent.ProcessDeleteCategory) },
//                    text = "Ya",
//                    modifier = Modifier
//                )
//            },
//            dismissButton = {
//                FilledButton(
//                    onClick = { events(ProductEvent.DismissAlertMessage) },
//                    text = "Tidak",
//                    modifier = Modifier
//                )
//            }
//        )
    }
}