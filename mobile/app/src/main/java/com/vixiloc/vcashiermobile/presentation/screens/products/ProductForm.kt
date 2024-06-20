package com.vixiloc.vcashiermobile.presentation.screens.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.vixiloc.vcashiermobile.commons.CurrencyFormatter
import com.vixiloc.vcashiermobile.domain.model.Variation
import com.vixiloc.vcashiermobile.presentation.components.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.commons.IconButton
import com.vixiloc.vcashiermobile.presentation.components.commons.LongTextField
import com.vixiloc.vcashiermobile.presentation.components.commons.TextField
import com.vixiloc.vcashiermobile.presentation.components.commons.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.components.products.DropdownMenu
import com.vixiloc.vcashiermobile.presentation.components.products.ImagePicker
import com.vixiloc.vcashiermobile.presentation.components.products.VariationDialog

enum class FormType {
    CREATE, UPDATE
}

@Composable
fun ProductForm(
    viewModel: ProductsViewModel,
    type: FormType
) {
    val state = viewModel.state
    val onEvent = viewModel::onEvent
    var expanded by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    ImagePicker(viewModel = viewModel)
    VerticalSpacer(height = 10.dp)
    TextField(
        value = state.productName,
        onValueChanged = {
            onEvent(ProductEvent.InputProductName(it))
        },
        modifier = Modifier,
        title = "Nama Produk",
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

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 2f)
    ) {
        items(state.variations) { variation: Variation ->
            Card(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.outlinedCardElevation(defaultElevation = 1.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                ),
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Unit")
                        Text(text = variation.unit)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Harga")
                        Text(text = CurrencyFormatter.formatCurrency(variation.price))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Harga Grosir")
                        Text(text = CurrencyFormatter.formatCurrency(variation.priceGrocery))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Stok")
                        Text(text = variation.stock.toString())
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        FilledButton(
                            onClick = {
                                onEvent(ProductEvent.UpdateVariation(variation))
                            },
                            text = "Ubah",
                            modifier = Modifier.width(screenWidth / 2.2f)
                        )
                        FilledButton(
                            onClick = {
                                onEvent(ProductEvent.DeleteVariation(variation))
                            },
                            text = "Hapus",
                            modifier = Modifier.width(screenWidth / 2.2f),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        )
                    }
                }
            }
        }
    }
    FilledButton(onClick = {
        if (type == FormType.CREATE) {
            onEvent(ProductEvent.SubmitCreateProduct)
        } else {
            onEvent(ProductEvent.SubmitUpdateProduct)
        }
    }, text = "Simpan", modifier = Modifier)

    VerticalSpacer(height = 150.dp)

    VariationDialog(visible = state.showVariationDialog, onDismiss = {
        onEvent(ProductEvent.ToggleVariationDialog)
    }, viewModel = viewModel, type = type)
}