package com.vixiloc.vcashiermobile.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme
import androidx.compose.material3.TextField as TextFieldCompose

@Composable
fun TextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier,
    title: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String = "",
    placeHolder: String = "",
    trailingIcon: @Composable() (() -> Unit) = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(text = title, style = textStyle.copy(fontWeight = FontWeight(600)))
        VerticalSpacer(height = 9.dp)
        TextFieldCompose(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFEBEBEB), shape = MaterialTheme.shapes.large),
            value = value,
            onValueChange = onValueChanged,
            singleLine = singleLine,
            textStyle = textStyle,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            enabled = enabled,
            isError = isError,
            placeholder = {
                Text(text = placeHolder, style = MaterialTheme.typography.bodySmall)
            },
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                unfocusedContainerColor = Color.Transparent,
                selectionColors = TextSelectionColors(
                    handleColor = Color.Red,
                    backgroundColor = Color.Green
                ),
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = trailingIcon
        )
        val errorVisibleState = remember { MutableTransitionState(isError) }
        AnimatedVisibility(visibleState = errorVisibleState) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Red),
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
fun LongTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier,
    title: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    singleLine: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(text = title, style = textStyle)
        VerticalSpacer(height = 9.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Black.copy(alpha = 0.05f),
                    shape = MaterialTheme.shapes.large
                ),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                singleLine = singleLine,
                textStyle = MaterialTheme.typography.bodySmall,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions
            )
        }
    }
}


@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    placeHolder: String = "Cari produk"
) {
    TextFieldCompose(
        modifier = modifier
            .background(color = Color.White, shape = MaterialTheme.shapes.large),
        value = value,
        onValueChange = onValueChanged,
        singleLine = singleLine,
        textStyle = textStyle,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        isError = isError,
        placeholder = {
            Text(text = placeHolder, style = MaterialTheme.typography.bodySmall.copy())
        },
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            errorContainerColor = MaterialTheme.colorScheme.errorContainer,
            unfocusedContainerColor = Color.Transparent,
            selectionColors = TextSelectionColors(
                handleColor = Color.Red,
                backgroundColor = Color.Green
            ),
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            FaIcon(faIcon = FaIcons.Search, tint = MaterialTheme.colorScheme.primary)
        },
        keyboardActions = keyboardActions
    )
}

@Preview(showBackground = true)
@Composable
private fun TextFieldPreview() {
    VcashierMobileTheme {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {

            }
        }
    }
}