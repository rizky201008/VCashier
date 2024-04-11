package com.vixiloc.vcashiermobile.presentation.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ramcosta.composedestinations.annotation.Destination
import com.vixiloc.vcashiermobile.presentation.widgets.home.DropdownMenu
import com.vixiloc.vcashiermobile.presentation.widgets.utils.FloatingTransactionButton
import com.vixiloc.vcashiermobile.presentation.widgets.utils.IconButton
import com.vixiloc.vcashiermobile.presentation.widgets.utils.ProductItem
import com.vixiloc.vcashiermobile.presentation.widgets.utils.VerticalSpacer

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "VCashier",
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
                )
            }, navigationIcon = {
                IconButton(onClick = { /*TODO*/ }, icon = Icons.Outlined.Menu)
            })
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = Color(0xFFF6F5F5))
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.Black.copy(alpha = 0.05f))
            ) {
                val (dropDown, searchButton, buttonBottom) = createRefs()
                val context = LocalContext.current
                val dropDownData =
                    arrayOf("Semua", "Cappuccino", "Espresso", "Latte", "Mocha")
                var expanded by remember { mutableStateOf(false) }
                var selectedText by remember { mutableStateOf(dropDownData[0]) }


                DropdownMenu(modifier = Modifier.constrainAs(dropDown) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.matchParent
                },
                    data = dropDownData,
                    onItemSelected = {
                        selectedText = it
                        Toast.makeText(context, "Selected: $it", Toast.LENGTH_SHORT).show()
                    },
                    selectedText = selectedText,
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                )

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .border(width = 1.dp, color = Color.Black.copy(alpha = 0.05f))
                        .constrainAs(searchButton) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            height = Dimension.fillToConstraints
                            width = Dimension.wrapContent
                        }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        Modifier.size(30.dp)
                    )
                }
            }
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (buttonBottom, lazyVerticalGrid) = createRefs()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .constrainAs(lazyVerticalGrid) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }) {
                    items(10) {
                        ProductItem(price = "Rp1000.000", name = "Cappuccino")
                    }
                    item {
                        VerticalSpacer(height = 100.dp)
                    }
                }
                FloatingTransactionButton(
                    onClick = { /*TODO*/ }, modifier = Modifier
                        .constrainAs(buttonBottom) {
                            bottom.linkTo(lazyVerticalGrid.bottom)
                            start.linkTo(lazyVerticalGrid.start)
                            end.linkTo(lazyVerticalGrid.end)
                        },
                    containerColor = MaterialTheme.colorScheme.primary,
                    icon = Icons.Outlined.ShoppingCart,
                    textStart = "2 Item",
                    textEnd = "Rp100.000"
                )
            }
        }
    }
}