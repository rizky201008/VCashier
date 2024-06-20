package com.vixiloc.vcashiermobile.presentation.components.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vixiloc.vcashiermobile.commons.CurrencyFormatter

@Composable
fun CustomerItem(
    modifier: Modifier = Modifier,
    headlineText: String,
    supportingText: String,
    showUpdateButton: Boolean = true,
    onUpdate: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large
            )
            .clip(shape = MaterialTheme.shapes.large)
            .clickable { onClick() },
        headlineContent = {
            Text(
                text = headlineText,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        supportingContent = {
            Text(
                text = "No. Telp: $supportingText",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        trailingContent = {
            if (showUpdateButton) {
                IconButton(onClick = { onUpdate() }) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transactionStatus: String,
    transactionTotal: String,
    transactionDate: String,
    transactionCustomer: String,
    onClick: () -> Unit = {}
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large
            )
            .clip(shape = MaterialTheme.shapes.large)
            .clickable { onClick() },
        headlineContent = {
            Text(
                text = transactionTotal,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
            )
        },
        supportingContent = {
            Column {
                Text(
                    text = "Status : $transactionStatus",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Tanggal : $transactionDate",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        overlineContent = {
            Text(
                text = "Pelanggan : $transactionCustomer",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}

@Composable
fun PaymentMethodItem(
    modifier: Modifier = Modifier,
    paymentMethodName: String,
    fee: Int,
    onClick: () -> Unit = {}
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large
            )
            .clip(shape = MaterialTheme.shapes.large)
            .clickable { onClick() },
        headlineContent = {
            Text(
                text = paymentMethodName,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        supportingContent = {
            Column {
                Text(
                    text = "Biaya : ${CurrencyFormatter.formatCurrency(fee)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}

