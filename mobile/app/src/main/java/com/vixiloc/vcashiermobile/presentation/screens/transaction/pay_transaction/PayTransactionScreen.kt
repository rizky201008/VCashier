package com.vixiloc.vcashiermobile.presentation.screens.transaction.pay_transaction

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.outlined.ArrowBackIosNew
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.R
import com.vixiloc.vcashiermobile.utils.CurrencyFormatter
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayTransactionScreen(
    modifier: Modifier = Modifier,
    navigator: NavHostController,
    navArgs: MainRoutes.NavDrawerScreens.Transactions.PayTransaction
) {
    val id = navArgs.id
    val viewModel: PayTransactionViewModel = koinViewModel()
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    val halfScreenWidth = (config.screenWidthDp / 2).dp

    LaunchedEffect(Unit) {
        onEvent(PayTransactionEvent.GetTransaction(id))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                title = {
                    Text(
                        text = "Bayar Pesanan",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight(600)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigator.navigateUp()
                        },
                        icon = Icons.Outlined.ArrowBackIosNew
                    )
                }
            )
        }
    ) { padding ->
        ConstraintLayout(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            val (content, buttonBottom) = createRefs()

            Column(
                modifier = modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(25.dp)
                    .constrainAs(content) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        Modifier.width(halfScreenWidth)
                    ) {
                        Text(
                            text = "Total Pembayaran",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .padding(bottom = 12.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            text = CurrencyFormatter.formatCurrency(
                                state.transactionData?.paymentAmount ?: 0
                            ),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Column(
                        Modifier.width(halfScreenWidth)
                    ) {
                        Text(
                            text = "Status",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .padding(bottom = 12.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            text = (state.transactionData?.paymentStatus
                                ?: "Undefined").replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                VerticalSpacer(height = 19.dp)

                HorizontalDivider(modifier = Modifier.padding(bottom = 10.dp))

                Loading(modifier = Modifier, visible = state.isLoading)

                if (state.transactionData?.paymentStatus == "paid") {
                    VerticalSpacer(height = 93.dp)
                    Image(
                        painter = painterResource(id = R.drawable.success_icon),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    VerticalSpacer(height = 25.dp)
                    Text(
                        text = "Pembayaran Telah Selesai",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    VerticalSpacer(height = 19.dp)
                    Text(
                        text = "Bank ${state.transactionData?.paymentMethod?.name}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    VerticalSpacer(height = 12.dp)
                    Row(
                        modifier = Modifier
                            .background(
                                color = Color.White,
                                shape = MaterialTheme.shapes.small
                            )
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.transactionData?.vaNumber ?: "Undefined",
                            style = MaterialTheme.typography.bodySmall,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.width(screenWidth / 1.5f)
                        )

                        IconButton(
                            onClick = {
                                // TODO: Implement copy to clipboard
                            },
                            icon = Icons.Default.ContentCopy,
                            filled = false
                        )
                    }

                    VerticalSpacer(height = 24.dp)

                    Text(
                        text = "Tata Cara Pembayaran",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.fillMaxWidth()
                    )
                    VerticalSpacer(height = 12.dp)
                    Text(
                        text = "Silahkan transfer ke rekening tersebut dengan bank yang sesuai dengan yang anda pilih. Transfer dengan nominal yang sesuai dengan total pembayaran diatas. Status pembayaran akan secara otomatis berubah jika anda selesai melakukan pembayaran.",
                        style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Justify),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Box(
                modifier = Modifier
                    .constrainAs(buttonBottom) {
                        width = Dimension.matchParent
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .background(color = Color.White)
                    .padding(horizontal = 24.dp, vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                FilledButton(
                    onClick = {
                        onEvent(PayTransactionEvent.CheckPaymentStatus(id))
                    },
                    text = "Cek Status",
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(700)),
                    contentPadding = PaddingValues(15.dp)
                )
            }

            MessageAlert(
                type = AlertType.SUCCESS,
                message = state.success,
                title = "Sukses",
                modifier = Modifier,
                visible = state.success.isNotEmpty(),
                onDismiss = {
                    onEvent(PayTransactionEvent.DismissAlertMessage)
                }
            )
            MessageAlert(
                type = AlertType.ERROR,
                message = state.error,
                title = "Error",
                modifier = Modifier,
                visible = state.error.isNotEmpty(),
                onDismiss = {
                    onEvent(PayTransactionEvent.DismissAlertMessage)
                }
            )
        }
    }
}

@Preview
@Composable
private fun PayTransactionScreenPreview() {
    VcashierMobileTheme {
        Surface {

        }
    }
}