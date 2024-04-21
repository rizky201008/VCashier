package com.vixiloc.vcashiermobile.presentation.widgets.commons

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.vixiloc.vcashiermobile.commons.Strings.TAG

@Composable
fun ProductItem(price: String, name: String, image: String? = null, onAdd: () -> Unit = {},context:Context) {
    Log.d(TAG, "ProductItem: $image")
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(
                image
                    ?: "https://cdn.pixabay.com/photo/2024/03/20/06/18/ai-generated-8644732_1280.jpg"
            )
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )

    Card(
        modifier = Modifier
            .width(160.dp)
            .padding(10.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.Crop
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        VerticalSpacer(height = 12.dp)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Text(
                text = price,
                style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
            )
            IconButton(onClick = onAdd, icon = Icons.Outlined.Add)
        }
        VerticalSpacer(height = 10.dp)
    }
}

@Composable
fun HorizontalProductItem(
    price: String,
    name: String,
    image: String? = null,
    onAdd: () -> Unit = {},
    onRemove: () -> Unit = {},
    onVariationClicked: () -> Unit = {}
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(
                image
                    ?: "https://cdn.pixabay.com/photo/2024/03/20/06/18/ai-generated-8644732_1280.jpg"
            )
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .width(130.dp)
                    .height(130.dp)
                    .clip(MaterialTheme.shapes.large),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onBackground),
                )
                Text(
                    text = price,
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.primary),
                )
                Badge(
                    modifier = Modifier.clickable { onVariationClicked() },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(text = "Variasi : ABcSdas", style = MaterialTheme.typography.bodySmall)
                }
                VerticalSpacer(height = 11.dp)
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 80.dp)
                ) {
                    IconButton(onClick = onAdd, icon = Icons.Outlined.Add)
                    Text(
                        text = "1",
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.primary)
                    )
                    IconButton(onClick = onRemove, icon = Icons.Outlined.Delete)
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun ProductItemPreview() {

}