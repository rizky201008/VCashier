package com.vixiloc.vcashiermobile.presentation.screens.products.components

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.guru.fontawesomecomposelib.FaIcons
import com.vixiloc.vcashiermobile.R
import com.vixiloc.vcashiermobile.domain.model.products.ProductsResponseItems
import com.vixiloc.vcashiermobile.domain.model.products.ProductsVariation
import com.vixiloc.vcashiermobile.domain.model.products.Variation
import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.OutlinedButton
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.screens.category.components.FilledIconButton
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme
import com.vixiloc.vcashiermobile.utils.CurrencyFormatter
import androidx.compose.material3.DropdownMenu as DropdownMenuCompose

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
fun VariationItem(
    modifier: Modifier = Modifier,
    variation: Variation,
    onUpdate: (Variation) -> Unit,
    onDelete: (Variation) -> Unit
) {
    Box(
        modifier = modifier
            .padding(bottom = 12.dp)
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                val rowModifier = Modifier.fillMaxWidth(0.6f)
                Row(
                    modifier = rowModifier,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Unit",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray)
                    )
                    Text(
                        text = variation.unit,
                        modifier = Modifier.width(80.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                    )
                }
                Row(
                    modifier = rowModifier,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Harga",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray)
                    )
                    Text(
                        text = CurrencyFormatter.formatCurrency(variation.price),
                        modifier = Modifier.width(80.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                    )
                }
                Row(
                    modifier = rowModifier,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Harga Grosir",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray)
                    )
                    Text(
                        text = CurrencyFormatter.formatCurrency(variation.priceGrocery),
                        modifier = Modifier.width(80.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                    )
                }
                Row(
                    modifier = rowModifier,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Stok",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray)
                    )
                    Text(
                        text = "${variation.stock}",
                        modifier = Modifier.width(80.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                    )
                }
            }
            Column {
                FilledIconButton(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small
                        ),
                    shape = MaterialTheme.shapes.small,
                    icon = Icons.Outlined.Edit,
                    onClick = {
                        onUpdate(variation)
                    },
                    iconSize = 16
                )
                VerticalSpacer(height = 8.dp)
                FilledIconButton(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = Color.Red.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small
                        ),
                    shape = MaterialTheme.shapes.small,
                    icon = Icons.Outlined.Delete,
                    iconTint = Color.Red,
                    onClick = {
                        onDelete(variation)
                    },
                    iconSize = 16
                )
            }
        }
    }
}

@Composable
fun VariationItemProductDetail(
    modifier: Modifier = Modifier,
    variation: Variation,
    onUpdate: (Variation) -> Unit,
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
                text = "5 KG",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight(600)
                )
            )
            Text(
                text = "Rp 58.000",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Rp 49.000 (Grosir)",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Rp 10.000 (Modal)",
                style = MaterialTheme.typography.bodySmall
            )
        }

        IconButton(onClick = { /*TODO*/ }, icon = FaIcons.Edit, containerSize = 40.dp)
    }
}

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    data: ProductsResponseItems,
    onClick: (ProductsResponseItems) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                onClick(data)
            }
    ) {
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
                .fillMaxWidth()
                .height(100.dp)
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = data.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(700))
            )
            Text(
                text = data.category.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 10.sp
                )
            )
        }
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