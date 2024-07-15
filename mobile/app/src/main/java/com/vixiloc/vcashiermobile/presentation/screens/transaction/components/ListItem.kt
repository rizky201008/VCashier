package com.vixiloc.vcashiermobile.presentation.screens.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import com.vixiloc.vcashiermobile.domain.model.transactions.TransactionsData
import com.vixiloc.vcashiermobile.presentation.components.HorizontalSpacer
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.utils.CurrencyFormatter
import com.vixiloc.vcashiermobile.utils.DateFormatter

@Composable
fun TransactionListItem(
    modifier: Modifier = Modifier,
    data: TransactionsData,
    onClick: (TransactionsData) -> Unit
) {
    val statusColor = when (data.transactionStatus) {
        "completed" -> Color(0xFF45B004)
        "pending" -> Color(0xFFE8A317)
        "canceled" -> Color(0xFFFF0000)
        else -> Color(0xFFE8A317)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick(data) }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = data.customer.name,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(600))
            )
            Row {
                FaIcon(
                    size = 16.dp,
                    faIcon = FaIcons.CalendarAltRegular,
                    tint = MaterialTheme.colorScheme.primary
                )
                HorizontalSpacer(width = 4.dp)
                Text(
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                        color = Color.Gray
                    ),
                    text = DateFormatter.format(data.createdAt!!)
                )
            }
        }
        VerticalSpacer(height = 16.dp)

        Text(
            text = buildAnnotatedString {
                append("Status: ")
                withStyle(
                    style = SpanStyle(
                        color = statusColor,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(data.transactionStatus.replaceFirstChar { it.uppercase() })
                }
            },
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
        )
        VerticalSpacer(height = 12.dp)
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
        VerticalSpacer(height = 8.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = CurrencyFormatter.formatCurrency(data.totalAmount),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}