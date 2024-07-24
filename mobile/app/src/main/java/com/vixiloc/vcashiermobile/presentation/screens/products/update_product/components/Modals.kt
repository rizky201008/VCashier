package com.vixiloc.vcashiermobile.presentation.screens.products.update_product.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.core.text.isDigitsOnly
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.TextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.screens.products.update_product.FormInputName
import com.vixiloc.vcashiermobile.presentation.screens.products.update_product.UpdateProductEvent
import com.vixiloc.vcashiermobile.presentation.screens.products.update_product.UpdateProductViewModel

@Composable
fun EditVariationDialog(modifier: Modifier = Modifier, viewModel: UpdateProductViewModel) {
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent
    if (state.showEditVariation) {
        Dialog(
            onDismissRequest = {
                onEvent(UpdateProductEvent.ShowEditVariation(false))
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
                        onEvent(UpdateProductEvent.ShowEditVariation(false))
                    }, icon = Icons.Outlined.Close)
                }
                VerticalSpacer(height = 10.dp)
                TextField(
                    value = state.variationName,
                    onValueChanged = {
                        onEvent(
                            UpdateProductEvent.ChangeInput(
                                FormInputName.VariationName,
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
                        if (it.isDigitsOnly()) {
                            onEvent(
                                UpdateProductEvent.ChangeInput(
                                    FormInputName.VariationPriceCapital,
                                    it
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    title = "Harga modal",
                    placeHolder = "Masukkan harga modal",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                TextField(
                    value = state.variationPrice,
                    onValueChanged = {
                        if (it.isDigitsOnly()) {
                            onEvent(
                                UpdateProductEvent.ChangeInput(
                                    FormInputName.VariationPrice,
                                    it
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    title = "Harga normal",
                    placeHolder = "Masukkan harga normal",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                TextField(
                    value = state.variationPriceGrocery,
                    onValueChanged = {
                        if (it.isDigitsOnly()) {
                            onEvent(
                                UpdateProductEvent.ChangeInput(
                                    FormInputName.VariationPriceGrocery,
                                    it
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    title = "Harga grosir",
                    placeHolder = "Masukkan harga grosir",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                VerticalSpacer(height = 32.dp)
                FilledButton(
                    onClick = {
                        onEvent(UpdateProductEvent.ShowEditVariation(false))
                        onEvent(UpdateProductEvent.SaveEditVariation)
                    },
                    text = "Ubah",
                    modifier = Modifier.fillMaxWidth(0.4f),
                    textStyle = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(600))
                )
            }
        }
    }
}

@Composable
fun SelectCategoryModal(modifier: Modifier = Modifier, viewModel: UpdateProductViewModel) {
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent
    if (state.showSearchCategory) {
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true
            ),
            onDismissRequest = {
                onEvent(UpdateProductEvent.ShowSearchCategory(false))
            }
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.background
                    )
                    .padding(25.dp)
            ) {
                item {
                    Loading(visible = state.isLoading)
                }
                items(state.categories) { category ->
                    CategoryListItem(item = category, onClick = {
                        onEvent(UpdateProductEvent.SelectCategory(category))
                        onEvent(UpdateProductEvent.ShowSearchCategory(false))
                    })
                    VerticalSpacer(height = 12.dp)
                }
            }
        }
    }
}