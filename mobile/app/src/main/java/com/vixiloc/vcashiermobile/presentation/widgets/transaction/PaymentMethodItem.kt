package com.vixiloc.vcashiermobile.presentation.widgets.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PaymentMethodItem(label: String, onCLick: () -> Unit, active: Boolean = false) {
    ListItem(
        modifier = Modifier
            .padding(10.dp)
            .heightIn(min = 100.dp)
            .background(color = Color.White, shape = MaterialTheme.shapes.large)
            .border(
                width = if (active) 1.dp else 0.dp,
                color = if (active) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = MaterialTheme.shapes.large
            )
            .clickable { onCLick() },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        headlineContent = {
            Text(
                text = label, style = MaterialTheme.typography.titleMedium
            )
        })
}