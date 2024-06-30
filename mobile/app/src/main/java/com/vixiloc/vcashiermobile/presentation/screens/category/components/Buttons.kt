package com.vixiloc.vcashiermobile.presentation.screens.category.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FilledIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    iconSize: Int = 24,
    shape: Shape = MaterialTheme.shapes.medium
) {
    Box(
        modifier = modifier
            .width(73.dp)
            .height(48.dp)
            .clip(shape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(iconSize.dp),
            imageVector = icon,
            contentDescription = null,
            tint = iconTint
        )
    }
}