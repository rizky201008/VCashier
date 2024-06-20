package com.vixiloc.vcashiermobile.presentation.components.products

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    modifier: Modifier,
    data: List<Map<String, Int>>,
    onItemSelected: (Int) -> Unit,
    selectedText: String,
    onSelectedTextChange: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    var itemData: List<Map<String, Int>> by remember { mutableStateOf(emptyList()) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            onExpandedChange(!expanded)
        },
        modifier = modifier
    ) {
        com.vixiloc.vcashiermobile.presentation.components.commons.TextField(
            value = selectedText,
            onValueChanged = {
                onSelectedTextChange(it)
                onExpandedChange(true)
                itemData = if (selectedText.isNotBlank()) {
                    data.filter { map ->
                        map.keys.any { key ->
                            key.contains(selectedText, ignoreCase = true)
                        }
                    }
                } else {
                    data
                }
            },
            modifier = Modifier.menuAnchor(),
            title = "Kategori",
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onExpandedChange(!expanded)
            }
        ) {
            val items = itemData.ifEmpty { data }
            items.forEach { item ->
                item.map { (key, value) ->
                    DropdownMenuItem(
                        text = { Text(text = key) },
                        onClick = {
                            onItemSelected(value)
                            onExpandedChange(!expanded)
                            onSelectedTextChange(key)
                        }
                    )
                }
            }
        }
    }
}