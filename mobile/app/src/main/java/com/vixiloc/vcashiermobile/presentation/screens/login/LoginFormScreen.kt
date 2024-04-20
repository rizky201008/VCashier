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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vixiloc.vcashiermobile.presentation.screens.destinations.MainScreenDestination
import com.vixiloc.vcashiermobile.presentation.widgets.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.widgets.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.widgets.commons.IconButton
import com.vixiloc.vcashiermobile.presentation.widgets.commons.Loading
import com.vixiloc.vcashiermobile.presentation.widgets.commons.MessageAlert
import com.vixiloc.vcashiermobile.presentation.widgets.commons.TextField
import com.vixiloc.vcashiermobile.presentation.widgets.commons.VerticalSpacer
import org.koin.androidx.compose.koinViewModel

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginFormScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state = viewModel.state
    val events = viewModel::onEvent
    val keyboardController = LocalSoftwareKeyboardController.current
    val showErrorAlert: Boolean = state.errorMessage.isNotBlank()
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Login Sekarang",
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
            Loading(modifier = Modifier, visible = state.isLoading)
            if (state.isLoading) keyboardController?.hide()
            if (state.loginSuccess) {
                navigator.popBackStack()
                navigator.navigate(MainScreenDestination)
            }
            MessageAlert(
                type = AlertType.ERROR,
                message = state.errorMessage,
                title = "Error",
                modifier = Modifier,
                visible = showErrorAlert,
                onDismiss = { events(LoginEvent.DismissAlertMessage) }
            )

            TextField(
                value = state.email,
                onValueChanged = {
                    events(LoginEvent.OnEmailChanged(it))
                },
                modifier = Modifier,
                title = "Email"
            )
            TextField(
                value = state.password,
                onValueChanged = {
                    events(LoginEvent.OnPasswordChanged(it))
                },
                modifier = Modifier,
                title = "Password",
                visualTransformation = PasswordVisualTransformation()
            )
            VerticalSpacer(height = 28.dp)
            FilledButton(
                text = "Login",
                onClick = {
                    events(LoginEvent.Login)
                },
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}