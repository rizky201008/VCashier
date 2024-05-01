package com.vixiloc.vcashiermobile.presentation.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.presentation.widgets.commons.FloatingTransactionButton
import com.vixiloc.vcashiermobile.presentation.widgets.commons.HorizontalProductItem
import com.vixiloc.vcashiermobile.presentation.widgets.commons.IconButton
import com.vixiloc.vcashiermobile.presentation.widgets.commons.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionReviewScreen(
    navigator: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Review Transaksi",
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
                )
            }, navigationIcon = {
                IconButton(
                    onClick = {
                        navigator.navigateUp()
                    },
                    icon = Icons.Outlined.ArrowBackIosNew
                )
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color(0xFFF6F5F5))
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (lazyColumn, buttonBottom) = createRefs()
                LazyColumn(
                    modifier = Modifier.constrainAs(lazyColumn) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    items(20) {
                        HorizontalProductItem(price = "Rp10.000", name = "Joko Kripto")
                    }
                    item {
                        VerticalSpacer(height = 100.dp)
                    }
                }
                FloatingTransactionButton(
                    onClick = {
                        navigator.popBackStack()
//                        navigator.navigate(TransactionPaymentScreenDestination)
                    }, modifier = Modifier
                        .constrainAs(buttonBottom) {
                            bottom.linkTo(lazyColumn.bottom)
                            start.linkTo(lazyColumn.start)
                            end.linkTo(lazyColumn.end)
                        },
                    containerColor = MaterialTheme.colorScheme.primary,
                    icon = null,
                    textStart = "2 Item",
                    textEnd = "Rp100.000"
                )
            }
        }
    }
}