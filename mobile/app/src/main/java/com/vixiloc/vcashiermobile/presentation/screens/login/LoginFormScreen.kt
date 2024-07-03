package com.vixiloc.vcashiermobile.presentation.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.TextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.IconButton as IconButtonCompose

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginFormScreen(modifier: Modifier = Modifier, navHostController: NavHostController) {
    val viewModel: LoginViewModel = koinViewModel()
    val state = viewModel.state
    val events = viewModel::onEvent
    val keyboardController = LocalSoftwareKeyboardController.current
    val showErrorAlert: Boolean = state.errorMessage.isNotBlank()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = modifier.padding(horizontal = 24.dp),
                title = {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight(600)
                        )
                    )
                },
//                navigationIcon = {
//                    IconButton(
//                        onClick = { /*TODO*/ },
//                        icon = Icons.Default.ArrowBackIosNew,
//                        modifier = Modifier.border(
//                            width = 0.5.dp,
//                            color = Color.LightGray,
//                            shape = CircleShape
//                        ),
//                        shape = CircleShape,
//                    )
//                }
            )
        }
    ) { pValue ->
        Column(
            modifier = Modifier
                .padding(pValue)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            VerticalSpacer(height = 40.dp)
            TextField(
                value = state.email,
                onValueChanged = {
                    events(LoginEvent.OnEmailChanged(it))
                },
                modifier = Modifier,
                title = "Email",
                placeHolder = "Masukkan email anda",
                textStyle = MaterialTheme.typography.titleMedium
            )
            VerticalSpacer(height = 12.dp)
            TextField(
                value = state.password,
                onValueChanged = {
                    events(LoginEvent.OnPasswordChanged(it))
                },
                modifier = Modifier,
                title = "Password",
                placeHolder = "Masukkan kata sandi anda",
                textStyle = MaterialTheme.typography.titleMedium,
                trailingIcon = {
                    IconButtonCompose(onClick = { /*TODO*/ }) {
                        FaIcon(
                            faIcon = FaIcons.EyeSlash,
                            tint = MaterialTheme.colorScheme.primary,
                            size = 16.dp
                        )
                    }
                }
            )
            VerticalSpacer(height = 52.dp)
            FilledButton(
                text = "Login",
                onClick = {
                    events(LoginEvent.Login)
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight(600))
            )

            Loading(modifier = Modifier, visible = state.isLoading)
            if (state.isLoading) keyboardController?.hide()
            if (state.loginSuccess) {
                LaunchedEffect(key1 = Unit) {
                    navHostController.popBackStack()
                    navHostController.navigate(MainRoutes.NavDrawerScreens)
                }
            }
            MessageAlert(
                type = AlertType.ERROR,
                message = state.errorMessage,
                title = "Error",
                modifier = Modifier,
                visible = showErrorAlert,
                onDismiss = { events(LoginEvent.DismissAlertMessage) }
            )
        }
    }
}