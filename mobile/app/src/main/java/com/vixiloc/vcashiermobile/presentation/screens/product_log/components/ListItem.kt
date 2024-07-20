package com.vixiloc.vcashiermobile.presentation.screens.product_log.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import com.vixiloc.vcashiermobile.domain.model.product_logs.ProductLogsResponseData
import com.vixiloc.vcashiermobile.domain.model.products.ProductsResponseItems
import com.vixiloc.vcashiermobile.domain.model.products.ProductsVariation
import com.vixiloc.vcashiermobile.presentation.components.HorizontalSpacer
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.utils.CurrencyFormatter
import com.vixiloc.vcashiermobile.utils.DateFormatter

@Composable
fun ProductLogListItem(
    modifier: Modifier = Modifier,
    data: ProductLogsResponseData,
    onClick: (ProductLogsResponseData) -> Unit
) {
    val statusColor = when (data.type) {
        "increase" -> Color(0xFF45B004)
        "decrease" -> Color(0xFFFF0000)
        else -> Color.Gray
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick(data) }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = data.productVariation.product.name + " " + data.productVariation.unit,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(600))
            )
            Row {
                FaIcon(
                    size = 16.dp,
                    faIcon = FaIcons.CalendarAltRegular,
                    tint = MaterialTheme.colorScheme.primary
                )
                HorizontalSpacer(width = 4.dp)
                Text(
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                        color = Color.Gray
                    ),
                    text = DateFormatter.format(data.createdAt)
                )
            }
        }
        VerticalSpacer(height = 16.dp)

        Text(
            text = buildAnnotatedString {
                append("Tipe: ")
                withStyle(
                    style = SpanStyle(
                        color = statusColor,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append((if (data.type == "increase") "Tambah" else "Kurangi"))
                }
            },
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
        )
        VerticalSpacer(height = 12.dp)
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
        VerticalSpacer(height = 8.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = data.amount.toString(),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                color = statusColor
            )
        }
    }
}

@Composable
fun ProductListItem(
    modifier: Modifier = Modifier,
    variation: ProductsVariation,
    data: ProductsResponseItems,
    onClick: (ProductsVariation) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding()
            .background(color = Color.White, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick(variation) }
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = data.name + " " + variation.unit,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Stok: " + variation.stock,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Tekan untuk menambahkan log",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}