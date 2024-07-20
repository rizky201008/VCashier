package com.vixiloc.vcashiermobile.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun Loading(modifier: Modifier = Modifier, visible: Boolean = false) {
    AnimatedVisibility(visible = visible, modifier = modifier, enter = fadeIn(), exit = fadeOut()) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(vertical = 10.dp)
            )
        }
    }
}

@Composable
fun MessageAlert(
    modifier: Modifier = Modifier,
    type: AlertType,
    message: String,
    title: String,
    visible: Boolean = false,
    onDismiss: () -> Unit = {},
    confirmButton: @Composable () -> Unit = {},
    dismissButton: @Composable () -> Unit = {},
) {
    AnimatedVisibility(visible = visible) {
        AlertDialog(onDismissRequest = onDismiss,
            dismissButton = dismissButton,
            confirmButton = confirmButton,
            icon = {
                Icon(
                    imageVector = generateAlertIcon(type = type),
                    contentDescription = null
                )
            },
            modifier = modifier,
            title = {
                Text(
                    text = title
                )
            },
            text = {
                Text(
                    text = message
                )
            })
    }
}

fun generateAlertIcon(type: AlertType): ImageVector {
    return when (type) {
        AlertType.INFO -> Icons.Outlined.Info
        AlertType.WARNING -> Icons.Outlined.Warning
        AlertType.ERROR -> Icons.Outlined.Error
        AlertType.SUCCESS -> Icons.Outlined.CheckCircle
    }
}

enum class AlertType {
    INFO,
    WARNING,
    ERROR,
    SUCCESS
}

@Preview
@Composable
private fun AlertPreview() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Loading(visible = true)
    }
}