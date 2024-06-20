package com.vixiloc.vcashiermobile.presentation.components.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
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

@Composable
fun CustomerItem(
    modifier: Modifier = Modifier,
    headlineText: String,
    supportingText: String,
    onDelete: () -> Unit,
    onUpdate: () -> Unit,
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
            Row {
                IconButton(onClick = { onUpdate() }) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(25.dp)
                    )
                }
                IconButton(onClick = { onDelete() }) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    )
}