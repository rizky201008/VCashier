package com.vixiloc.vcashiermobile.presentation.screens.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.commons.CurrencyFormatter
import com.vixiloc.vcashiermobile.domain.model.Variation
import com.vixiloc.vcashiermobile.presentation.widgets.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.widgets.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.widgets.commons.IconButton
import com.vixiloc.vcashiermobile.presentation.widgets.commons.Loading
import com.vixiloc.vcashiermobile.presentation.widgets.commons.LongTextField
import com.vixiloc.vcashiermobile.presentation.widgets.commons.MessageAlert
import com.vixiloc.vcashiermobile.presentation.widgets.commons.TableCell
import com.vixiloc.vcashiermobile.presentation.widgets.commons.TextField
import com.vixiloc.vcashiermobile.presentation.widgets.commons.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.widgets.products.DropdownMenu
import com.vixiloc.vcashiermobile.presentation.widgets.products.ImagePicker
import com.vixiloc.vcashiermobile.presentation.widgets.products.VariationDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateProductScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ProductsViewModel = koinViewModel(),
    currencyFormatter: CurrencyFormatter = CurrencyFormatter()
) {
    val state = viewModel.state
    val onEvent = viewModel::onEvent
    var expanded by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    LaunchedEffect(key1 = Unit) {
        viewModel.processGetCategories()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {
        ImagePicker(viewModel = viewModel)
        VerticalSpacer(height = 10.dp)
        TextField(
            value = state.productName,
            onValueChanged = {
                onEvent(ProductEvent.InputProductName(it))
            },
            modifier = Modifier,
            title = "Nama Produk"
        )
        LongTextField(
            value = state.productDescription,
            onValueChanged = {
                onEvent(ProductEvent.InputProductDescription(it))
            },
            modifier = Modifier,
            title = "Deskripsi Produk",
            singleLine = false
        )

        DropdownMenu(
            modifier = Modifier,
            data = state.categories,
            onItemSelected = { categoryId: Int ->
                onEvent(ProductEvent.InputProductCategory(categoryId))
            },
            selectedText = state.categoryName,
            expanded = expanded,
            onExpandedChange = {
                expanded = it
            },
            onSelectedTextChange = {
                onEvent(ProductEvent.InputProductCategoryName(it))
            }
        )

        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Variasi",
                modifier = Modifier,
                style = MaterialTheme.typography.bodySmall
            )
            IconButton(onClick = {
                onEvent(ProductEvent.ToggleVariationDialog)
            }, icon = Icons.Outlined.Add)
        }
        val column1Weight = .3f // 30%
        val column2Weight = .7f // 70%

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 1.5f)
        ) {
            items(state.variations) { variation: Variation ->
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(Modifier.fillMaxWidth()) {
                        TableCell(text = "Unit", weight = column1Weight)
                        TableCell(text = variation.unit, weight = column2Weight)
                    }
                    Row(Modifier.fillMaxWidth()) {
                        TableCell(text = "Harga", weight = column1Weight)
                        TableCell(
                            text = currencyFormatter.formatCurrency(variation.price),
                            weight = column2Weight
                        )
                    }
                    Row(Modifier.fillMaxWidth()) {
                        TableCell(text = "Harga Grosir", weight = column1Weight)
                        TableCell(
                            text = currencyFormatter.formatCurrency(variation.priceGrocery),
                            weight = column2Weight
                        )
                    }
                    Row(Modifier.fillMaxWidth()) {
                        TableCell(text = "Stok", weight = column1Weight)
                        TableCell(text = variation.stock.toString(), weight = column2Weight)
                    }
                    VerticalSpacer(height = 10.dp)
                }
            }
        }
        FilledButton(onClick = {
            onEvent(ProductEvent.SubmitCreateProduct)
        }, text = "Simpan", modifier = Modifier)

        VerticalSpacer(height = 300.dp)

        VariationDialog(visible = state.showVariationDialog, onDismiss = {
            onEvent(ProductEvent.ToggleVariationDialog)
        }, viewModel = viewModel)

        Loading(modifier = Modifier, visible = state.isLoading)

        MessageAlert(
            type = AlertType.ERROR,
            message = state.error,
            title = "Error",
            modifier = Modifier,
            visible = state.error.isNotBlank(),
            onDismiss = { onEvent(ProductEvent.DismissAlertMessage) }
        )

        MessageAlert(
            type = AlertType.SUCCESS,
            message = state.success,
            title = "Success",
            modifier = Modifier,
            visible = state.success.isNotBlank(),
            onDismiss = {
                onEvent(ProductEvent.DismissAlertMessage)
                navController.navigateUp()
            }
        )
    }
}