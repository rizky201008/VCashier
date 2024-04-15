package com.vixiloc.vcashiermobile.presentation.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vixiloc.vcashiermobile.presentation.screens.destinations.TransactionSuccessScreenDestination
import com.vixiloc.vcashiermobile.presentation.widgets.transaction.CashPayment
import com.vixiloc.vcashiermobile.presentation.widgets.transaction.CashlessPayment
import com.vixiloc.vcashiermobile.presentation.widgets.utils.FilledButton
import com.vixiloc.vcashiermobile.presentation.widgets.utils.IconButton

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionPaymentScreen(navigator: DestinationsNavigator) {
    var tabState by remember { mutableStateOf(0) }
    val titles = listOf("Tunai", "Non Tunai")
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Pembayaran",
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    navigator.navigateUp()
                }, icon = Icons.Outlined.ArrowBackIosNew)
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = Color(0xFFF6F5F5))
                .verticalScroll(state = rememberScrollState())
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (content, bottomButton, topRow, tabRow) = createRefs()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(topRow) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .background(color = Color.White),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val textModifier = Modifier.padding(10.dp)
                    Text(
                        text = "Total Pembayaran",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = textModifier
                    )
                    Text(
                        text = "Rp200.000",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = textModifier
                    )
                }
                PrimaryTabRow(
                    modifier = Modifier.constrainAs(tabRow) {
                        top.linkTo(topRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    selectedTabIndex = tabState,
                    containerColor = Color.White
                ) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            selected = tabState == index,
                            onClick = { tabState = index },
                            text = {
                                Text(
                                    text = title,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                        )
                    }
                }
                val contentModifier = Modifier
                    .constrainAs(content) {
                        top.linkTo(tabRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .height(600.dp)
                when (tabState) {
                    0 -> {
                        CashPayment(
                            modifier = contentModifier

                        )
                    }

                    1 -> {
                        CashlessPayment(modifier = contentModifier)
                    }
                }

                FilledButton(
                    onClick = {
                        navigator.popBackStack()
                        navigator.navigate(TransactionSuccessScreenDestination)
                    },
                    text = "Konfirmasi",
                    modifier = Modifier
                        .constrainAs(bottomButton) {
                            start.linkTo(content.start)
                            end.linkTo(content.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(10.dp)
                )
            }
        }
    }
}