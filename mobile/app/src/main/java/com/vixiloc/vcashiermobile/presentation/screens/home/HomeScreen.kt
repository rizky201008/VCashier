package com.vixiloc.vcashiermobile.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.guru.fontawesomecomposelib.FaIcons
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.home.components.MenuIcon
import com.vixiloc.vcashiermobile.utils.Strings.TAG
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel: HomeViewModel = koinViewModel()
    Log.i(TAG, "HomeScreen: Lagi di Home")
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
            text = viewModel.role.value.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        VerticalSpacer(height = 25.dp)

        LazyHorizontalGrid(rows = GridCells.Fixed(3)) {
            item {
                MenuIcon(icon = FaIcons.Boxes, label = "Produk", onClick = {
                    navController.navigate(MainRoutes.NavDrawerScreens.Products) {
                        popUpTo(MainRoutes.NavDrawerScreens.Home)
                        launchSingleTop = true
                    }
                })
            }
        }
    }
}