package com.vixiloc.vcashiermobile.presentation.components.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vixiloc.vcashiermobile.utils.CurrencyFormatter
import com.vixiloc.vcashiermobile.domain.model.products.ProductResponseItems
import com.vixiloc.vcashiermobile.domain.model.products.ProductsVariation
import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems
import com.vixiloc.vcashiermobile.presentation.components.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.commons.IconButton
import com.vixiloc.vcashiermobile.presentation.components.commons.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme

@Composable
fun AddToCartModal(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = { },
    variation: ProductsVariation,
    product: ProductResponseItems,
    onConfirm: (CartItems) -> Unit = { }
) {
    var amount by remember { mutableIntStateOf(0) }
    var isGrocery by remember { mutableStateOf(false) }
    val productPrice = if (isGrocery) variation.priceGrocery else variation.price
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium
                )
                .verticalScroll(state = rememberScrollState())
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Masukkan Jumlah",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight(600)
                    )
                )

                IconButton(onClick = onDismissRequest, icon = Icons.Outlined.Close)
            }
            VerticalSpacer(height = 25.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Harga",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight(600)
                        )
                    )
                    Text(
                        text = CurrencyFormatter.formatCurrency(productPrice),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        if (amount > 0) {
                            amount--
                        }
                    }, icon = Icons.Outlined.Delete)
                    OutlinedTextField(
                        modifier = Modifier
                            .width(70.dp)
                            .padding(10.dp),
                        value = amount.toString(),
                        onValueChange = { },
                        singleLine = true,
                        readOnly = true,
                        textStyle = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center),
                        shape = MaterialTheme.shapes.medium,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    IconButton(onClick = {
                        if (amount < variation.stock) {
                            amount++
                        }
                    }, icon = Icons.Outlined.Add)
                }
            }
            VerticalSpacer(height = 10.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Harga Grosir", style = MaterialTheme.typography.bodyMedium)
                Switch(checked = isGrocery, onCheckedChange = {
                    isGrocery = it
                })
            }
            VerticalSpacer(height = 10.dp)
            HorizontalDivider()
            VerticalSpacer(height = 10.dp)
            Text(
                text = "Subtotal",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight(600))
            )
            Text(
                text = CurrencyFormatter.formatCurrency(productPrice * amount),
                style = MaterialTheme.typography.bodySmall
            )
            VerticalSpacer(height = 10.dp)
            FilledButton(
                onClick = {
                    val data = CartItems().apply {
                        variationId = variation.id
                        quantity = amount
                        grocery = isGrocery
                        price = productPrice
                        imageUrl = product.imageUrl
                        name = "${product.name} - ${variation.unit}"
                        maxStock = variation.stock
                    }
                    onConfirm(data)
                    onDismissRequest()
                },
                text = "Konfirmasi",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun ModalPrev() {
    VcashierMobileTheme {

    }
}