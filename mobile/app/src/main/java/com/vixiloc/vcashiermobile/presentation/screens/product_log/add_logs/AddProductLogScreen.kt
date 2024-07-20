package com.vixiloc.vcashiermobile.presentation.screens.product_log.add_logs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.SearchTextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.product_log.components.AddLogDialog
import com.vixiloc.vcashiermobile.presentation.screens.product_log.components.ProductListItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductLogScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel: AddProductLogViewModel = koinViewModel()
    val state = viewModel.state.value
    val focusManager = LocalFocusManager.current
    val onEvent = viewModel::onEvent
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                title = {
                    Text(
                        text = "Log Produk",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight(600)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                        icon = Icons.Outlined.ArrowBackIosNew
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(25.dp)
        ) {
            SearchTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.searchQuery,
                onValueChanged = {
                    onEvent(AddProductLogEvent.SearchProduct(it))
                },
                placeHolder = "Cari Produk",
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            )
            VerticalSpacer(height = 24.dp)
            LazyColumn {
                item {
                    Loading(visible = state.isLoading)
                }
                items(state.products) { product ->
                    product.variations.forEach { variation ->
                        ProductListItem(
                            data = product,
                            variation = variation,
                            onClick = {
                                onEvent(AddProductLogEvent.SelectVariation(variation))
                                onEvent(AddProductLogEvent.ShowAddLogDialog(true))
                            }
                        )
                        VerticalSpacer(height = 12.dp)
                    }
                }
                item {
                    VerticalSpacer(height = 300.dp)
                }
            }

            AddLogDialog(viewModel = viewModel)
        }
    }
}