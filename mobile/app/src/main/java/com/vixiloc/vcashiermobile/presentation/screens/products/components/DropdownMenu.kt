package com.vixiloc.vcashiermobile.presentation.screens.products.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem
import com.vixiloc.vcashiermobile.presentation.components.TextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    modifier: Modifier,
    data: List<CategoriesResponseItem>,
    onItemSelected: (CategoriesResponseItem) -> Unit,
    selectedText: String,
    onSelectedTextChange: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    var itemData: List<CategoriesResponseItem> by remember { mutableStateOf(emptyList()) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            onExpandedChange(!expanded)
        },
        modifier = modifier
    ) {
        TextField(
            value = selectedText,
            onValueChanged = {
                onSelectedTextChange(it)
                onExpandedChange(true)
                itemData = if (selectedText.isNotBlank()) {
                    data.filter { data ->
                        data.name.contains(selectedText, ignoreCase = true)
                    }
                } else {
                    data
                }
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier.menuAnchor(),
            title = "Kategori",
            textStyle = MaterialTheme.typography.bodySmall
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onExpandedChange(!expanded)
            }
        ) {
            val items = itemData.ifEmpty { data }
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.name) },
                    onClick = {
                        onItemSelected(item)
                        onExpandedChange(!expanded)
                        onSelectedTextChange(item.name)
                    }
                )
            }
        }
    }
}