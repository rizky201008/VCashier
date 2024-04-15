package com.vixiloc.vcashiermobile.presentation.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vixiloc.vcashiermobile.presentation.screens.destinations.HomeScreenDestination
import com.vixiloc.vcashiermobile.presentation.widgets.utils.FilledButton
import com.vixiloc.vcashiermobile.presentation.widgets.utils.IconButton
import com.vixiloc.vcashiermobile.presentation.widgets.utils.TextField
import com.vixiloc.vcashiermobile.presentation.widgets.utils.VerticalSpacer

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginFormScreen(role: String, navigator: DestinationsNavigator) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Login Sebagai $role",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }, navigationIcon = {
            IconButton(
                onClick = { navigator.navigateUp() },
                icon = Icons.Outlined.ArrowBackIosNew
            )
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TextField(
                value = "Apa Mungkin",
                onValueChanged = {},
                modifier = Modifier,
                title = "Email"
            )
            TextField(
                value = "Apa Mungkin",
                onValueChanged = {},
                modifier = Modifier,
                title = "Password",
                visualTransformation = PasswordVisualTransformation()
            )
            VerticalSpacer(height = 28.dp)
            FilledButton(
                text = "Login",
                onClick = {
                    navigator.popBackStack()
                    navigator.navigate(HomeScreenDestination)
                },
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}