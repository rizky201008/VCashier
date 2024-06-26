package com.vixiloc.vcashiermobile.presentation.screens.transaction.checkout

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.commons.CurrencyFormatter
import com.vixiloc.vcashiermobile.domain.model.customers.CustomerResponseItem
import com.vixiloc.vcashiermobile.presentation.components.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.components.commons.FloatingTransactionButton
import com.vixiloc.vcashiermobile.presentation.components.commons.IconButton
import com.vixiloc.vcashiermobile.presentation.components.commons.Loading
import com.vixiloc.vcashiermobile.presentation.components.commons.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.commons.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.components.products.TransactionHorizontalProductItem
import com.vixiloc.vcashiermobile.presentation.components.transaction.AddCustomerButton
import com.vixiloc.vcashiermobile.presentation.components.transaction.TransactionCustomerItem
import com.vixiloc.vcashiermobile.presentation.navigations.CheckoutScreens
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navigator: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: CheckoutScreenViewModel = koinViewModel(),
    customer: CustomerResponseItem?
) {
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        onEvent(CheckoutScreenEvent.UpdateCustomer(customer))
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                title = {
                    Text(
                        text = "Checkout",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight(600)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            (context as? Activity)?.finish()
                        },
                        icon = Icons.Outlined.ArrowBackIosNew
                    )
                }
            )
        }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
        ) {
            val (mainContent, button) = createRefs()
            Column(
                modifier = Modifier
                    .constrainAs(mainContent) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(25.dp)
            ) {

                Text(
                    modifier = Modifier,
                    text = "Produk",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight(600))
                )

                VerticalSpacer(height = 24.dp)

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((120 * state.cartItems.size).dp)
                ) {
                    items(state.cartItems) {
                        TransactionHorizontalProductItem(
                            data = it,
                            onDelete = { onEvent(CheckoutScreenEvent.DeleteCartItem(it)) }
                        )
                        VerticalSpacer(height = 24.dp)
                    }
                }

                Text(
                    modifier = Modifier,
                    text = "Pelanggan",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight(600))
                )

                val customerModifier = Modifier
                state.customer?.let {
                    TransactionCustomerItem(
                        customer = it,
                        modifier = customerModifier,
                        onClick = { navigator.navigate(CheckoutScreens.SearchCustomer) })
                } ?: run {
                    AddCustomerButton(onClick = {
                        navigator.navigate(CheckoutScreens.SearchCustomer)
                    }, modifier = customerModifier)
                }
            }

            Box(
                modifier = Modifier
                    .constrainAs(button) {
                        width = Dimension.matchParent
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .background(color = Color.White)
                    .padding(horizontal = 24.dp, vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                FloatingTransactionButton(
                    modifier = Modifier,
                    onClick = {
                        onEvent(CheckoutScreenEvent.CreateTransaction)
                    },
                    icon = Icons.Outlined.ShoppingCart,
                    textStart = "${state.cartItems.count()} Item",
                    textEnd = CurrencyFormatter.formatCurrency(state.cartItems.sumOf { it.price })
                )
            }

            MessageAlert(
                type = AlertType.ERROR,
                message = state.error,
                title = "Error",
                visible = state.error.isNotBlank(),
                onDismiss = { onEvent(CheckoutScreenEvent.DismissErrorAlert) }
            )

            MessageAlert(
                type = AlertType.SUCCESS,
                message = state.success,
                title = "Sukses",
                modifier = Modifier,
                visible = state.success.isNotEmpty(),
                onDismiss = {
                    onEvent(CheckoutScreenEvent.DismissSuccessAlert)
                    navigator.navigate(CheckoutScreens.MakePayment(state.transactionId))
                }
            )

            if (state.validationErrors.isNotEmpty()) {
                MessageAlert(
                    type = AlertType.ERROR,
                    message = state.validationErrors.first(),
                    title = "Error",
                    visible = true,
                    onDismiss = { onEvent(CheckoutScreenEvent.DismissErrorAlert) }
                )
            }

            Loading(modifier = Modifier, visible = state.isLoading)
        }
    }
}