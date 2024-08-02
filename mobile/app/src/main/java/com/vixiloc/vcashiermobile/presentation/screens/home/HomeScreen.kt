package com.vixiloc.vcashiermobile.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.R
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.home.components.MenuIcon
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onTitleChange: (String) -> Unit
) {
    val viewModel: HomeViewModel = koinViewModel()
    val state = viewModel.state.value
    LaunchedEffect(key1 = Unit) {
        onTitleChange("Home")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
        Text(
            text = "Selamat datang!",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(600))
        )
        Text(
            text = state.role.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        VerticalSpacer(height = 12.dp)

        Image(
            modifier = Modifier
                .width(141.dp)
                .align(CenterHorizontally),
            painter = painterResource(id = R.drawable.undraw_welcoming_re_x0qo),
            contentDescription = null
        )

        VerticalSpacer(height = 25.dp)

        Text(
            text = "Menu",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        VerticalSpacer(height = 12.dp)

        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(state.listHomeMenu) { menu ->
                MenuIcon(icon = menu.icon, label = menu.name, onClick = {
                    navController.navigate(menu.route) {
                        popUpTo(MainRoutes.NavDrawerScreens.Home)
                        launchSingleTop = true
                    }
                    onTitleChange(menu.name)
                })
            }
        }
    }
}