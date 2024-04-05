package com.vixiloc.vcashiermobile.presentation.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
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
import com.vixiloc.vcashiermobile.presentation.widgets.home.DropdownMenu
import com.vixiloc.vcashiermobile.presentation.widgets.products.ProductItem
import com.vixiloc.vcashiermobile.presentation.widgets.utils.IconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = {
                    Text(
                        text = "VCashier",
                        style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
                    )
                }, navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }, icon = Icons.Outlined.Menu)
                })
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 1.dp, color = Color.Black.copy(alpha = 0.05f))
                ) {
                    val (dropDown, searchButton) = createRefs()
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
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(7.dp)
                    ) {
                        Icon(imageVector = Icons.Outlined.ShoppingCart, contentDescription = null)
                        Text(text = "2 Item", style = MaterialTheme.typography.titleSmall)
                    }
                    Text(
                        text = "Total: Rp200.000",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = Color(0xFFF6F5F5))
        ) {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(10) {
                    ProductItem(price = "Rp1000.000", name = "Cappuccino")
                }
            }
        }
    }
}