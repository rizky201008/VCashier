package com.vixiloc.vcashiermobile.presentation.screens.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.vixiloc.vcashiermobile.presentation.MainActivity
import com.vixiloc.vcashiermobile.presentation.components.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.components.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.commons.Loading
import com.vixiloc.vcashiermobile.presentation.components.commons.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.commons.TextField
import com.vixiloc.vcashiermobile.presentation.components.commons.VerticalSpacer
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginFormScreen(
    viewModel: LoginViewModel = koinViewModel()
) {
    val state = viewModel.state
    val events = viewModel::onEvent
    val keyboardController = LocalSoftwareKeyboardController.current
    val showErrorAlert: Boolean = state.errorMessage.isNotBlank()
    val context: Context = LocalContext.current

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Login Sekarang",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            Loading(modifier = Modifier, visible = state.isLoading)
            if (state.isLoading) keyboardController?.hide()
            if (state.loginSuccess) {
                context.startActivity(Intent(context, MainActivity::class.java))
                (context as? Activity)?.finish()
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
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}