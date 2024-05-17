package com.vixiloc.vcashiermobile.presentation.widgets.products

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.vixiloc.vcashiermobile.presentation.screens.products.ProductEvent
import com.vixiloc.vcashiermobile.presentation.screens.products.ProductsViewModel
import com.vixiloc.vcashiermobile.presentation.widgets.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.widgets.commons.TextField

@Composable
fun VariationDialog(
    modifier: Modifier = Modifier,
    visible: Boolean = false,
    onDismiss: () -> Unit = {},
    viewModel: ProductsViewModel
) {
    val state = viewModel.state
    val onEvent = viewModel::onEvent
    val configuration = LocalConfiguration.current

    AnimatedVisibility(visible = visible) {
        Dialog(
            onDismissRequest = onDismiss,
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = MaterialTheme.shapes.large
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = state.variationUnit,
                        onValueChanged = {
                            onEvent(ProductEvent.InputVariationUnit(it))
                        },
                        modifier = Modifier,
                        title = "Nama Variasi",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextField(
                        value = state.variationPrice,
                        onValueChanged = {
                            if (it.isDigitsOnly()) {
                                onEvent(ProductEvent.InputVariationPrice(it))
                            }
                        },
                        modifier = Modifier,
                        title = "Harga",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextField(
                        value = state.variationPriceGrocery.toString(),
                        onValueChanged = {
                            if (it.isDigitsOnly()) {
                                onEvent(ProductEvent.InputVariationPriceGrocery(it))
                            }
                        },
                        modifier = Modifier,
                        title = "Harga Grosir",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextField(
                        value = state.variationStock.toString(),
                        onValueChanged = {
                            if (it.isDigitsOnly()) {
                                onEvent(ProductEvent.InputVariationStock(it))
                            }
                        },
                        modifier = Modifier,
                        title = "Stok",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    FilledButton(
                        onClick = {
                            onEvent(ProductEvent.ToggleVariationDialog)
                            onEvent(ProductEvent.SubmitAddVariation)
                        },
                        text = "Simpan",
                        modifier = Modifier
                    )
                }
            }
        }
    }
}