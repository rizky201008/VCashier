package com.vixiloc.vcashiermobile.presentation.screens.products.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.TextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.screens.products.create_product.CreateProductEvent
import com.vixiloc.vcashiermobile.presentation.screens.products.create_product.CreateProductViewModel
import com.vixiloc.vcashiermobile.presentation.screens.products.create_product.InputName

@Composable
fun AddVariationDialog(modifier: Modifier = Modifier, viewModel: CreateProductViewModel) {
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent
    Dialog(
        onDismissRequest = {
            onEvent(CreateProductEvent.ShowAddVariationDialog(false))
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium
                )
                .verticalScroll(state = rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tambah Variasi",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight(600)
                    )
                )

                IconButton(onClick = {
                    onEvent(CreateProductEvent.ShowAddVariationDialog(false))
                }, icon = Icons.Outlined.Close)
            }
            VerticalSpacer(height = 10.dp)
            TextField(
                value = state.variationUnit,
                onValueChanged = {
                    onEvent(
                        CreateProductEvent.ChangeInput(
                            InputName.VARIATION_UNIT,
                            it
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                title = "",
                placeHolder = "Nama variasi",
            )
            TextField(
                value = state.variationPriceCapital,
                onValueChanged = {
                    onEvent(
                        CreateProductEvent.ChangeInput(
                            InputName.VARIATION_PRICE_CAPITAL,
                            it
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                title = "",
                placeHolder = "Masukkan harga modal",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            TextField(
                value = state.variationPrice,
                onValueChanged = {
                    onEvent(
                        CreateProductEvent.ChangeInput(
                            InputName.VARIATION_PRICE,
                            it
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                title = "",
                placeHolder = "Masukkan harga normal",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            TextField(
                value = state.variationPriceGrocery,
                onValueChanged = {
                    onEvent(
                        CreateProductEvent.ChangeInput(
                            InputName.VARIATION_PRICE_GROCERY,
                            it
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                title = "",
                placeHolder = "Masukkan harga grosir",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            TextField(
                value = state.variationStock,
                onValueChanged = {
                    onEvent(
                        CreateProductEvent.ChangeInput(
                            InputName.VARIATION_STOCK,
                            it
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                title = "",
                placeHolder = "Masukkan stok",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            VerticalSpacer(height = 32.dp)
            FilledButton(
                onClick = {
                    onEvent(CreateProductEvent.ShowAddVariationDialog(false))
                    onEvent(CreateProductEvent.AddVariation)
                },
                text = "Tambah",
                modifier = Modifier.fillMaxWidth(0.4f),
                textStyle = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(600))
            )
        }
    }
}