package com.vixiloc.vcashiermobile.presentation.screens.products.list_products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import com.vixiloc.vcashiermobile.R
import com.vixiloc.vcashiermobile.domain.model.products.ProductsResponseItems
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.PainterIconButton
import com.vixiloc.vcashiermobile.presentation.components.SearchTextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.products.components.ProductItem
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductsScreen(modifier: Modifier = Modifier, onNavigate: (MainRoutes) -> Unit) {
    val viewModel: ProductsViewModel = koinViewModel()
    val state = viewModel.state.value
    val events = viewModel::onEvent
    var menuExpanded by remember { mutableStateOf(false) }
    val config = LocalConfiguration.current
    val focusManager = LocalFocusManager.current
    val screenWidth = config.screenWidthDp.dp

    LaunchedEffect(key1 = Unit) {
        events(ProductEvent.Refresh)
    }

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (mainContent, bottomButton) = createRefs()
        Column(
            modifier = Modifier
                .constrainAs(mainContent) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Row(
                Modifier
                    .background(color = Color.White)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledButton(
                    onClick = {
                        menuExpanded = !menuExpanded
                    },
                    text = state.selectedCategory.name,
                    modifier = Modifier,
                    trailingIcon = Icons.Outlined.KeyboardArrowDown
                )
                DropdownMenu(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .width(screenWidth / 2f),
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                ) {
                    DropdownMenuItem(
                        text = { Text(state.selectedCategory.name) },
                        onClick = {
                            events(ProductEvent.SelectCategory(category = null))
                            menuExpanded = false
                        }
                    )
                    state.categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                events(ProductEvent.SelectCategory(category = category))
                                menuExpanded = false
                            }
                        )
                    }
                }

                Row {
                    PainterIconButton(
                        onClick = { onNavigate(MainRoutes.NavDrawerScreens.Products.ProductLog) },
                        icon = painterResource(id = R.drawable.product_log),
                        containerSize = 40.dp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(state = rememberScrollState())
                    .padding(24.dp)
            ) {
                SearchTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    onValueChanged = {

                    },
                    placeHolder = "Cari Produk",
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                )

                Loading(modifier = Modifier, visible = state.isLoading)
                VerticalSpacer(height = 37.dp)

                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(900.dp),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    items(state.products) { product: ProductsResponseItems ->
                        ProductItem(
                            data = product,
                            onClick = {
                                onNavigate(MainRoutes.NavDrawerScreens.Products.UpdateProduct(id = product.id.toString()))
                            }
                        )
                    }
                    item {
                        VerticalSpacer(height = 200.dp)
                    }
                    item {
                        VerticalSpacer(height = 200.dp)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .constrainAs(bottomButton) {
                    width = Dimension.matchParent
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .background(color = Color.White)
                .padding(horizontal = 24.dp, vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            FilledButton(
                onClick = {
                    onNavigate(MainRoutes.NavDrawerScreens.Products.CreateProduct)
                },
                text = "Tambah Produk",
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(700)),
                contentPadding = PaddingValues(15.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ProductsScreenPreview() {
    VcashierMobileTheme {
        Surface {

        }
    }
}