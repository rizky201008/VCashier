package com.vixiloc.vcashiermobile.presentation.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.MaterialTheme
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
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vixiloc.vcashiermobile.presentation.screens.destinations.TransactionReviewScreenDestination
import com.vixiloc.vcashiermobile.presentation.widgets.commons.FloatingTransactionButton
import com.vixiloc.vcashiermobile.presentation.widgets.commons.IconButton
import com.vixiloc.vcashiermobile.presentation.widgets.commons.ProductItem
import com.vixiloc.vcashiermobile.presentation.widgets.commons.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.widgets.home.DropdownMenu

@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFF6F5F5))
    ) {
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
                    },
                icon = Icons.Outlined.Search
            )
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
                onClick = {
                    navigator.navigate(TransactionReviewScreenDestination)
                }, modifier = Modifier
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