package com.vixiloc.vcashiermobile.presentation.components.products

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenu as DropdownMenuCompose
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.vixiloc.vcashiermobile.R
import com.vixiloc.vcashiermobile.utils.CurrencyFormatter
import com.vixiloc.vcashiermobile.domain.model.products.ProductsVariation
import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems
import com.vixiloc.vcashiermobile.presentation.components.commons.IconButton
import com.vixiloc.vcashiermobile.presentation.components.commons.OutlinedButton
import com.vixiloc.vcashiermobile.presentation.components.commons.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme

@Composable
fun TransactionProductItem(
    modifier: Modifier = Modifier,
    variations: List<ProductsVariation> = emptyList(),
    name: String,
    image: String? = null,
    onAdd: (ProductsVariation) -> Unit = {},
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var selectedVariation: ProductsVariation? by remember { mutableStateOf(null) }

    Card(
        modifier = modifier
            .width(152.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        AsyncImage(
            model = image,
            error = painterResource(
                id = R.drawable.gmbr_placeholder
            ),
            placeholder = painterResource(
                id = R.drawable.gmbr_placeholder
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(94.dp)
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.Crop
        )
        VerticalSpacer(height = 7.dp)
        Column(
            modifier = Modifier
                .padding(horizontal = 9.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier
            )
            VerticalSpacer(height = 14.dp)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = {
                        menuExpanded = !menuExpanded
                    },
                    text = if (selectedVariation == null) "Pilih Varian" else selectedVariation!!.unit,
                    modifier = Modifier.width((152 / 1.3f).dp),
                    trailingIcon = Icons.Outlined.KeyboardArrowDown,
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp
                    ),
                    contentPadding = PaddingValues(2.dp)
                )
                DropdownMenuCompose(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background),
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                ) {
                    variations.forEach { variation ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    variation.unit + " " + CurrencyFormatter.formatCurrency(
                                        variation.price
                                    )
                                )
                            },
                            onClick = {
                                selectedVariation = variation
                                menuExpanded = false
                            }
                        )
                    }
                }
                IconButton(
                    onClick = {
                        selectedVariation?.let {
                            onAdd(it)
                        }
                    },
                    icon = Icons.Outlined.Add,
                    filled = true,
                    containerSize = 30.dp
                )
            }
        }
    }
}

@Composable
fun TransactionHorizontalProductItem(
    modifier: Modifier = Modifier,
    data: CartItems,
    onDelete: (CartItems) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    var total by remember {
        mutableIntStateOf(data.price * data.quantity)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                color = Color.White,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = data.imageUrl,
                error = painterResource(
                    id = R.drawable.gmbr_placeholder
                ),
                placeholder = painterResource(
                    id = R.drawable.gmbr_placeholder
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .clip(MaterialTheme.shapes.large),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .width(screenWidth / 2f)
            ) {
                Text(
                    text = data.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight(600),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = CurrencyFormatter.formatCurrency(total),
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.primary,
                    ),
                    modifier = Modifier
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                ) {
                    Text(
                        text = "${data.quantity} Pcs",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(onClick = { onDelete(data) }, icon = Icons.Outlined.Delete)
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    price: String,
    context: Context,
    name: String,
    image: String? = null,
    category: String,
    onClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    var size by remember { mutableStateOf(IntSize.Zero) }
    val elemetShape: Shape = MaterialTheme.shapes.large.copy(
        bottomStart = CornerSize(0.dp),
        bottomEnd = CornerSize(0.dp)
    )
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
        modifier = modifier
            .width((screenWidth / 2f) - 5.dp)
            .padding((screenWidth / 60f))
            .shadow(elevation = 5.dp, shape = elemetShape)
            .onSizeChanged { size = it }
            .clip(
                shape = elemetShape
            )
            .clickable { onClick() },
        shape = elemetShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height((size.height.toFloat() / 4f).dp)
                .clip(
                    shape = elemetShape
                ),
            contentScale = ContentScale.Crop
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        VerticalSpacer(height = 2.dp)
        Text(
            text = category,
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        VerticalSpacer(height = 2.dp)
        Text(
            text = price,
            style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.primary),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        VerticalSpacer(height = 10.dp)
    }
}

@Preview(showBackground = false)
@Composable
private fun ProductItemPreview() {
    VcashierMobileTheme {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp),
                contentAlignment = Alignment.Center
            ) {
            }
        }
    }
}