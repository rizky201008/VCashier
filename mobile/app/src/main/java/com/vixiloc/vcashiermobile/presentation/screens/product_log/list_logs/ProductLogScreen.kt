package com.vixiloc.vcashiermobile.presentation.screens.product_log.list_logs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.product_log.components.ProductLogListItem
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductLogScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel: ProductLogViewModel = koinViewModel()
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent

    LaunchedEffect(key1 = Unit) {
        onEvent(ProductLogEvent.Refresh)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                title = {
                    Text(
                        text = "Log perubahan stok",
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
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(MainRoutes.NavDrawerScreens.Products.ProductLogAdd)
                        },
                        icon = Icons.Outlined.Add
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(25.dp)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(state.logs) { item ->
                    ProductLogListItem(data = item, onClick = {})
                    VerticalSpacer(height = 12.dp)
                }
            }
            Loading(visible = state.isLoading)
            MessageAlert(
                type = AlertType.SUCCESS,
                message = state.success,
                title = "Sukses",
                modifier = Modifier,
                visible = state.showSuccess,
                onDismiss = {
                    onEvent(ProductLogEvent.ShowSuccessAlert(false))
                }
            )
            MessageAlert(
                type = AlertType.ERROR,
                message = state.error,
                title = "Error",
                modifier = Modifier,
                visible = state.showError,
                onDismiss = {
                    onEvent(ProductLogEvent.ShowErrorAlert(false))
                }
            )
        }
    }

}

@Preview
@Composable
private fun ProductLogScreenPreview() {
    VcashierMobileTheme {
        Surface {

        }
    }
}