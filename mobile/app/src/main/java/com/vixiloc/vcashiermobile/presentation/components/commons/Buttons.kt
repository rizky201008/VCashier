package com.vixiloc.vcashiermobile.presentation.components.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.FaIcons
import androidx.compose.material3.OutlinedButton as OutlinedButtonCompose

@Composable
fun IconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    filled: Boolean = false,
    containerSize: Dp = 34.dp
) {
    Box(
        modifier = modifier
            .background(
                color = if (filled) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = shape
            )
            .size(containerSize)
            .clip(shape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(containerSize / 2.5f),
            tint = if (filled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun PainterIconButton(
    onClick: () -> Unit,
    icon: Painter,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    filled: Boolean = false,
    containerSize: Dp = 34.dp
) {
    Box(
        modifier = modifier
            .background(
                color = if (filled) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = shape
            )
            .size(containerSize)
            .clip(shape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(containerSize / 2.5f),
            tint = if (filled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun FilledButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    trailingIcon: Any? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        colors = colors,
        contentPadding = contentPadding
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier
        )
        trailingIcon?.let {
            when (it) {
                is FaIconType -> FaIcon(
                    faIcon = it,
                    tint = MaterialTheme.colorScheme.onPrimary
                )

                is ImageVector -> Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                // Add other cases if needed
            }
        }
    }
}

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    trailingIcon: Any? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding
) {
    OutlinedButtonCompose(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        colors = colors,
        contentPadding = contentPadding
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.padding(10.dp),
            overflow = TextOverflow.Ellipsis
        )
        trailingIcon?.let {
            when (it) {
                is FaIconType -> FaIcon(
                    faIcon = it,
                    tint = ButtonDefaults.outlinedButtonColors().contentColor
                )

                is ImageVector -> Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = ButtonDefaults.outlinedButtonColors().contentColor
                )
            }
        }
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
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

    }
}