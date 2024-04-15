package com.vixiloc.vcashiermobile.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vixiloc.vcashiermobile.R
import com.vixiloc.vcashiermobile.presentation.screens.destinations.LoginFormScreenDestination
import com.vixiloc.vcashiermobile.presentation.screens.destinations.LoginScreenDestination
import com.vixiloc.vcashiermobile.presentation.widgets.utils.FilledButton
import com.vixiloc.vcashiermobile.presentation.widgets.utils.IconButton
import com.vixiloc.vcashiermobile.presentation.widgets.utils.HorizontalLogo
import com.vixiloc.vcashiermobile.presentation.widgets.utils.VerticalSpacer

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            })
        },

        ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VerticalSpacer(height = 22.5.dp)
            HorizontalLogo()
            VerticalSpacer(height = 45.dp)
            Image(
                painter = painterResource(id = R.drawable.img_login),
                contentDescription = null,
                modifier = Modifier
                    .width(162.dp)
                    .height(187.dp)
            )
            VerticalSpacer(height = 29.dp)
            Text(text = "Masuk akun VCashier", style = MaterialTheme.typography.bodyMedium)
            VerticalSpacer(height = 29.dp)
            FilledButton(
                text = "Admin",
                onClick = {
                    navigator.popBackStack()
                    navigator.navigate(LoginFormScreenDestination("Admin"))
                },
                modifier = Modifier
                    .padding(vertical = 5.5.dp)
                    .padding(horizontal = 20.dp)
            )
            FilledButton(
                text = "Cashier",
                onClick = {
                    navigator.navigate(LoginFormScreenDestination("Cashier"))
                },
                modifier = Modifier
                    .padding(vertical = 5.5.dp)
                    .padding(horizontal = 20.dp)
            )
            FilledButton(
                text = "Warehouse",
                onClick = {
                    navigator.popBackStack()
                    navigator.navigate(LoginFormScreenDestination("Warehouse"))
                },
                modifier = Modifier
                    .padding(vertical = 5.5.dp)
                    .padding(horizontal = 20.dp)
            )
        }
    }
}