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
import com.vixiloc.vcashiermobile.R
import com.vixiloc.vcashiermobile.presentation.widgets.utils.BackButton
import com.vixiloc.vcashiermobile.presentation.widgets.utils.HorizontalLogo
import com.vixiloc.vcashiermobile.presentation.widgets.utils.VButton
import com.vixiloc.vcashiermobile.presentation.widgets.utils.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            },
                navigationIcon = {
                    BackButton(onClick = { /*TODO*/ }, icon = Icons.Outlined.ArrowBackIosNew)
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
            VButton(
                text = "Admin",
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(vertical = 5.5.dp)
                    .padding(horizontal = 20.dp)
            )
            VButton(
                text = "Cashier",
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(vertical = 5.5.dp)
                    .padding(horizontal = 20.dp)
            )
            VButton(
                text = "Warehouse",
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(vertical = 5.5.dp)
                    .padding(horizontal = 20.dp)
            )
        }
    }
}