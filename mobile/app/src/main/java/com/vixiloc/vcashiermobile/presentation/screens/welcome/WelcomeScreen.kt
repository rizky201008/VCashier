package com.vixiloc.vcashiermobile.presentation.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.R
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val viewModel: WelcomeViewModel = koinViewModel()
    val state = viewModel.state.value

    if (state.screenReady) {
        if (state.token.isNotEmpty()) {
            LaunchedEffect(key1 = Unit) {
                navHostController.popBackStack()
                navHostController.navigate(MainRoutes.NavDrawerScreens)
            }
        } else {
            LaunchedEffect(key1 = Unit) {
                navHostController.popBackStack()
                navHostController.navigate(MainRoutes.LoginScreen)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .size(82.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_cart),
                    contentDescription = null
                )
            }
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}