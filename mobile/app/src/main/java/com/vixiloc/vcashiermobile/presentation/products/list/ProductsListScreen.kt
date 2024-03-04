package com.vixiloc.vcashiermobile.presentation.products.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.vixiloc.vcashiermobile.R

@Composable
fun ProductsListScreen(modifier: Modifier) {
    var searchInputValue by remember {
        mutableStateOf(
            TextFieldValue(text = "")
        )
    }

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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            items(10) {
                ProductListItem()
            }
        }
    }
}

@Composable
fun ProductListItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.app),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 125.dp)
                .clip(shape = MaterialTheme.shapes.medium)
        )
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = "Nama Produk", style = MaterialTheme.typography.titleMedium)
            Text(text = "Stok: 10", style = MaterialTheme.typography.bodySmall)
            Text(text = "Rp10.000", style = MaterialTheme.typography.bodyMedium)
        }
    }
}