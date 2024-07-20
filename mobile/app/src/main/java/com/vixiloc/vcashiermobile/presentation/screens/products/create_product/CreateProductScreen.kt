package com.vixiloc.vcashiermobile.presentation.screens.products.create_product
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.billythelittle.lazycolumns.LazyColumnScrollbarSettings
import com.billythelittle.lazycolumns.LazyColumnWithScrollbar
import com.vixiloc.vcashiermobile.domain.model.categories.CategoriesResponseItem
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.LongTextField
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.TextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.screens.category.components.FilledIconButton
import com.vixiloc.vcashiermobile.presentation.screens.products.components.AddVariationDialog
import com.vixiloc.vcashiermobile.presentation.screens.products.components.DeleteVariationDialog
import com.vixiloc.vcashiermobile.presentation.screens.products.components.DropdownMenu
import com.vixiloc.vcashiermobile.presentation.screens.products.components.EditVariationDialog
import com.vixiloc.vcashiermobile.presentation.screens.products.components.VariationItem
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class
)
@Composable
fun CreateProductScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val viewModel: CreateProductViewModel = koinViewModel()
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent
    val primaryColor = MaterialTheme.colorScheme.primary
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                title = {
                    Text(
                        text = "Tambah Produk",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = primaryColor,
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
        ConstraintLayout(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            val (mainContent, bottomButton) = createRefs()
            val scrollState = rememberScrollState()
            val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = { uri -> onEvent(CreateProductEvent.ChangeImage(uri)) }
            )
            val focusManager = LocalFocusManager.current

            Column(
                modifier = Modifier
                    .constrainAs(mainContent) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(25.dp)
                    .verticalScroll(state = scrollState)
            ) {
                Text(
                    text = "Foto Produk",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight(700)),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                val stroke = Stroke(
                    width = 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 30f), 0f)
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(112.dp)
                        .background(
                            color = Color(0xFFE8F1FC),
                            shape = MaterialTheme.shapes.medium
                        )
                        .drawBehind {
                            drawRoundRect(
                                color = primaryColor,
                                style = stroke,
                                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                            )
                        }
                        .clickable {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        state.image?.let {
                            AsyncImage(
                                model = state.image,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } ?: run {
                            Icon(
                                imageVector = Icons.Outlined.Image,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp),
                                tint = primaryColor
                            )
                            Text(
                                text = "Tambahkan foto produk",
                                style = MaterialTheme.typography.bodySmall.copy(color = primaryColor)
                            )
                        }
                    }
                }
                VerticalSpacer(height = 24.dp)
                TextField(
                    value = state.productName,
                    onValueChanged = {
                        onEvent(CreateProductEvent.ChangeInput(InputName.PRODUCT_NAME, it))
                    },
                    modifier = Modifier,
                    title = "Nama Produk"
                )
                VerticalSpacer(height = 24.dp)
                LongTextField(
                    value = state.productDescription,
                    onValueChanged = {
                        onEvent(CreateProductEvent.ChangeInput(InputName.PRODUCT_DESCRIPTION, it))
                    },
                    modifier = Modifier,
                    title = "Deskripsi Produk"
                )

                var expanded by remember { mutableStateOf(false) }

                VerticalSpacer(height = 24.dp)
                DropdownMenu(
                    modifier = Modifier,
                    data = state.categories,
                    onItemSelected = { category: CategoriesResponseItem ->
                        onEvent(CreateProductEvent.SelectCategory(category))
                    },
                    selectedText = state.selectedCategory?.name ?: "Pilih Kategori",
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = it
                    },
                    onSelectedTextChange = {
                        focusManager.clearFocus()
                    },
                )
                VerticalSpacer(height = 24.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Variasi",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(700))
                    )
                    FilledIconButton(
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                shape = MaterialTheme.shapes.small
                            ),
                        shape = MaterialTheme.shapes.small,
                        icon = Icons.Outlined.Add,
                        onClick = {
                            onEvent(CreateProductEvent.ShowAddVariationDialog(true))
                        },
                        iconSize = 14
                    )
                }
                VerticalSpacer(height = 12.dp)
                val scrollbarSettings = remember {
                    mutableStateOf(LazyColumnScrollbarSettings())
                }
                LazyColumnWithScrollbar(
                    data = state.variations,
                    settings = scrollbarSettings.value,
                    modifier = Modifier.height(204.dp)
                ) {
                    items(state.variations) { variation ->
                        VariationItem(
                            variation = variation,
                            onUpdate = {
                                onEvent(CreateProductEvent.SelectVariation(it))
                                onEvent(CreateProductEvent.ShowEditVariationDialog(true))
                            },
                            onDelete = {
                                onEvent(CreateProductEvent.SelectVariation(it))
                                onEvent(CreateProductEvent.ShowDeleteVariationDialog(true))
                            }
                        )
                    }
                }
                Loading(modifier = Modifier, visible = state.isLoading)
                VerticalSpacer(height = 300.dp)
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
                    .padding(horizontal = 24.dp, vertical = 14.dp)
                    .padding(bottom = 26.dp),
                contentAlignment = Alignment.Center
            ) {
                FilledButton(
                    onClick = {
                        onEvent(CreateProductEvent.CreateProduct)
                    },
                    text = "Tambah Produk",
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(700)),
                    contentPadding = PaddingValues(15.dp)
                )
            }

            MessageAlert(
                type = AlertType.ERROR,
                message = state.error,
                title = "Error",
                visible = state.showError,
                onDismiss = {
                    onEvent(CreateProductEvent.ShowErrorMessage(false))
                }
            )

            MessageAlert(
                type = AlertType.SUCCESS,
                message = state.success,
                title = "Sukses",
                modifier = Modifier,
                visible = state.showSuccess,
                onDismiss = {
                    onEvent(CreateProductEvent.ShowSuccessMessage(false))
                }
            )
            if (state.showAddVariation) {
                AddVariationDialog(viewModel = viewModel)
            }
            if (state.showEditVariation) {
                EditVariationDialog(viewModel = viewModel)
            }
            DeleteVariationDialog(viewModel = viewModel)
        }
    }
}

@Preview
@Composable
private fun CreateProductScreenPreview() {
    VcashierMobileTheme {
        Surface {
        }
    }
}