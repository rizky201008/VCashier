package com.vixiloc.vcashiermobile.presentation.screens.products.update_product

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.guru.fontawesomecomposelib.FaIcons
import com.vixiloc.vcashiermobile.R
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.TextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.products.create_product.CreateProductEvent
import com.vixiloc.vcashiermobile.presentation.screens.products.update_product.components.EditVariationDialog
import com.vixiloc.vcashiermobile.presentation.screens.products.update_product.components.SelectCategoryModal
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme
import com.vixiloc.vcashiermobile.utils.CurrencyFormatter
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProductScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    args: MainRoutes.NavDrawerScreens.Products.UpdateProduct
) {
    val viewModel: UpdateProductViewModel = koinViewModel()
    val onEvent = viewModel::onEvent
    val state = viewModel.state.value
    val focusManager = LocalFocusManager.current
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            onEvent(UpdateProductEvent.SetProductImageUri(uri))
            onEvent(UpdateProductEvent.SaveEditImage)
        }
    )

    LaunchedEffect(key1 = Unit) {
        onEvent(UpdateProductEvent.GetProduct(args.id))
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                title = {
                    Text(
                        text = "Detail produk",
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
                            onEvent(UpdateProductEvent.SetEditMode(!state.editMode))
                        },
                        if (state.editMode) FaIcons.Save else FaIcons.Edit,
                        containerSize = 35.dp
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .padding(25.dp)
                .verticalScroll(state = rememberScrollState())
        ) {
            Loading(visible = state.isLoading)
            AsyncImage(
                model = state.productImage,
                error = painterResource(
                    id = R.drawable.gmbr_placeholder
                ),
                placeholder = painterResource(
                    id = R.drawable.gmbr_placeholder
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(300.dp)
                    .clip(MaterialTheme.shapes.large)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentScale = ContentScale.Crop
            )

            VerticalSpacer(height = 10.dp)

            if (state.editMode) {
                TextField(
                    value = state.productName,
                    onValueChanged = {
                        onEvent(UpdateProductEvent.ChangeInput(FormInputName.ProductName, it))
                    },
                    singleLine = false,
                    textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight(600))
                )
            } else {
                Text(
                    text = state.productName,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight(600))
                )
            }

            VerticalSpacer(height = 10.dp)

            if (state.editMode) {
                Button(onClick = { onEvent(UpdateProductEvent.ShowSearchCategory(true)) }) {
                    Text(text = state.productCategory, style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Text(
                        text = "Kategori : ",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(600)),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = state.productCategory,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(600)),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }


            VerticalSpacer(height = 10.dp)

            HorizontalDivider()

            VerticalSpacer(height = 10.dp)
            if (state.editMode) {
                TextField(
                    value = state.productDescription,
                    onValueChanged = {
                        onEvent(
                            UpdateProductEvent.ChangeInput(
                                FormInputName.ProductDescription,
                                it
                            )
                        )
                    },
                    singleLine = false,
                    textStyle = MaterialTheme.typography.bodySmall
                )
            } else {
                Text(
                    text = state.productDescription,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            VerticalSpacer(height = 10.dp)

            HorizontalDivider()

            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = "Variasi",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            LazyColumn(modifier = Modifier.height(500.dp)) {
                items(state.productVariations) { variation ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White, shape = MaterialTheme.shapes.medium)
                            .padding(10.dp)
                    ) {
                        val screenWidth = LocalConfiguration.current.screenWidthDp
                        val columnWidth = (screenWidth / 1.4).dp
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.width(columnWidth)
                            ) {
                                Text(
                                    text = variation.unit,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight(600)
                                    )
                                )
                                Text(
                                    text = CurrencyFormatter.formatCurrency(variation.price),
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = CurrencyFormatter.formatCurrency(variation.priceGrocery) + " (Grosir)",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = CurrencyFormatter.formatCurrency(
                                        variation.priceCapital ?: 0
                                    ) + " (Modal)",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            IconButton(
                                onClick = {
                                    onEvent(UpdateProductEvent.SelectVariation(variation))
                                    onEvent(UpdateProductEvent.ShowEditVariation(true))
                                },
                                icon = FaIcons.Edit,
                                containerSize = 40.dp
                            )
                        }
                    }
                }
            }
            MessageAlert(
                type = AlertType.SUCCESS,
                message = state.success,
                title = "Sukses",
                modifier = Modifier,
                visible = state.showSuccess,
                onDismiss = {
                    onEvent(UpdateProductEvent.ShowSuccess(false))
                    onEvent(UpdateProductEvent.GetProduct(args.id))
                }
            )
            MessageAlert(
                type = AlertType.ERROR,
                message = state.error,
                title = "Error",
                modifier = Modifier,
                visible = state.showError,
                onDismiss = {
                    onEvent(UpdateProductEvent.ShowError(false))
                }
            )
            EditVariationDialog(viewModel = viewModel)
            SelectCategoryModal(viewModel = viewModel)
        }
    }
}

@Preview
@Composable
private fun UpdateProductPreview() {
    VcashierMobileTheme {
        Surface {
//            UpdateProductScreen()
        }
    }
}