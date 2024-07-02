package com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TransactionStatusChip(
    modifier: Modifier = Modifier,
    name: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val textColor =
        if (enabled) MaterialTheme.colorScheme.onPrimary else Color.Gray
    val containerColor = if (enabled) MaterialTheme.colorScheme.primary else Color.LightGray
    Box(
        modifier = modifier
            .padding(0.dp)
            .background(color = containerColor, shape = MaterialTheme.shapes.large)
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = name,
            color = textColor,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(600))
        )
    }
}