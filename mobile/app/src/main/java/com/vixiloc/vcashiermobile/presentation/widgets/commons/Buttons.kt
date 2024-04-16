package com.vixiloc.vcashiermobile.presentation.widgets.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedButton as OutlinedButtonCompose

@Composable
fun IconButton(onClick: () -> Unit, icon: ImageVector, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
            .size(34.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            Modifier.size(15.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun FilledButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    OutlinedButtonCompose(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.padding(10.dp)
        )
    }

}

@Composable
fun FloatingTransactionButton(
    modifier: Modifier,
    onClick: () -> Unit,
    icon: ImageVector?,
    textStart: String,
    textEnd: String,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Box(modifier = modifier
        .padding(10.dp)
        .background(color = containerColor, shape = MaterialTheme.shapes.large)
        .clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                icon?.let {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = contentColor
                    )
                }
                Text(
                    text = textStart,
                    style = MaterialTheme.typography.titleSmall.copy(color = contentColor)
                )
            }
            Text(
                text = textEnd,
                style = MaterialTheme.typography.titleSmall.copy(color = contentColor),
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ButtonPrev() {
    IconButton(onClick = { /*TODO*/ }, icon = Icons.Default.ArrowBackIosNew)
}