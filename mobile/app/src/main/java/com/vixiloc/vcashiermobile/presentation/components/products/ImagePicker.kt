package com.vixiloc.vcashiermobile.presentation.components.products

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vixiloc.vcashiermobile.presentation.screens.products.ProductEvent
import com.vixiloc.vcashiermobile.presentation.screens.products.ProductsViewModel

@Composable
fun ImagePicker(modifier: Modifier = Modifier, viewModel: ProductsViewModel) {
    val state = viewModel.state
    val onEvent = viewModel::onEvent
    var boxWidth by remember { mutableStateOf(0.dp) }
    // Registers a photo picker activity launcher in single-select mode.

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> onEvent(ProductEvent.InputProductImage(uri)) }
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .onSizeChanged { boxWidth = it.width.dp }
            .height(boxWidth / 3)
            .background(color = Color.Gray.copy(alpha = 0.5f))
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
                        .width(boxWidth / 6f)
                        .height(boxWidth / 6f)
                )
                Text(text = "Pilih gambar")
            }
        }
    }
}