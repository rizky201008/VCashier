package com.vixiloc.vcashiermobile.presentation.categories.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun CategoriesListScreen(modifier: Modifier) {
    var searchInputValue by remember {
        mutableStateOf(
            TextFieldValue(text = "")
        )
    }
    val lazyListState = rememberLazyListState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = searchInputValue,
            onValueChange = {
                searchInputValue = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            shape = MaterialTheme.shapes.large,
            trailingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            },
            singleLine = true,
        )

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.FilterAlt, contentDescription = null)
            }
        }

        LazyColumn(modifier = Modifier.height(800.dp), state = lazyListState) {
            items(10) {
                CategoryListItem(
                    modifier = Modifier.fillMaxWidth(),
                    name = "Kategori $it",
                    product = 200
                )
            }
        }
    }
}

@Composable
fun CategoryListItem(
    modifier: Modifier,
    name: String,
    product: Int,
) {
    ListItem(
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .background(color = Color.White, shape = MaterialTheme.shapes.medium),
        headlineContent = {
            Text(text = name, style = MaterialTheme.typography.bodyLarge)
        },
        supportingContent = {
            Text(text = "Produk :$product", style = MaterialTheme.typography.bodySmall)
        }
    )
}