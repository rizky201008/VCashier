package com.vixiloc.vcashiermobile.presentation.components.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import com.vixiloc.vcashiermobile.commons.CurrencyFormatter
import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem
import com.vixiloc.vcashiermobile.domain.model.payments.PaymentMethodData
import com.vixiloc.vcashiermobile.presentation.components.commons.HorizontalSpacer
import com.vixiloc.vcashiermobile.presentation.components.commons.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme

@Composable
fun TransactionCustomerItem(
    modifier: Modifier = Modifier,
    customer: CustomerResponseItem,
    onClick: (CustomerResponseItem) -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(color = Color.White, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick(customer) }
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 15.dp)
    ) {
        Text(
            text = customer.name,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(600))
        )
        VerticalSpacer(height = 3.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            FaIcon(
                faIcon = FaIcons.Phone,
                modifier = Modifier.rotate(90f),
                tint = MaterialTheme.colorScheme.primary,
                size = 10.dp
            )
            Text(
                text = customer.phoneNumber ?: "-",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray)
            )
        }
    }
}

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transactionStatus: String,
    transactionTotal: String,
    transactionDate: String,
    transactionCustomer: String,
    onClick: () -> Unit = {}
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large
            )
            .clip(shape = MaterialTheme.shapes.large)
            .clickable { onClick() },
        headlineContent = {
            Text(
                text = transactionTotal,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
            )
        },
        supportingContent = {
            Column {
                Text(
                    text = "Status : $transactionStatus",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Tanggal : $transactionDate",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        overlineContent = {
            Text(
                text = "Pelanggan : $transactionCustomer",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}

@Composable
fun PaymentMethodItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    selected: Boolean = false,
    paymentMethods: PaymentMethodData
) {
    val containerColor = if (selected) MaterialTheme.colorScheme.primary else Color.White
    val contentColor = if (selected) Color.White else MaterialTheme.colorScheme.primary

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .background(
                color = containerColor,
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(Modifier.fillMaxWidth(0.4f), verticalAlignment = Alignment.CenterVertically) {
            RadioList(active = selected)
            HorizontalSpacer(width = 18.dp)
            Text(
                text = paymentMethods.name,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = contentColor,
                    fontWeight = FontWeight(600)
                )
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text(
                text = "Biaya admin",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = contentColor,
                    fontWeight = FontWeight(400),
                    fontSize = 8.sp
                )
            )
            Text(
                text = CurrencyFormatter.formatCurrency(paymentMethods.fee),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = contentColor,
                    fontWeight = FontWeight(600)
                )
            )
        }
    }
}

@Composable
fun RadioList(modifier: Modifier = Modifier, active: Boolean = false) {
    Box(
        modifier = modifier
            .size(12.dp)
            .background(color = if (active) Color.White else Color.LightGray, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (active) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.White,
                                MaterialTheme.colorScheme.primary
                            )
                        ), shape = CircleShape
                    )
            )
        }
    }
}

@Preview
@Composable
fun TransactionListItemPreview() {
    VcashierMobileTheme {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {

            }
        }
    }
}
