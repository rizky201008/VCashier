package com.vixiloc.vcashiermobile.presentation.widgets.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vixiloc.vcashiermobile.presentation.widgets.home.DropdownMenu

@OptIn(ExperimentalMaterial3Api::class)
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
                .height(50.dp)
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
                keyboardOptions = keyboardOptions,
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

@Preview(showBackground = true)
@Composable
private fun TextFieldPreview() {

}