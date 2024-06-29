package com.vixiloc.vcashiermobile.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.navs.routes.MainRoutes
import com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction.CreateTransactionScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: (MainRoutes) -> Unit
) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    var menuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledButton(
                onClick = {
                    menuExpanded = !menuExpanded
                },
                text = "All Product",
                modifier = Modifier,
                trailingIcon = Icons.Outlined.KeyboardArrowDown
            )
            DropdownMenu(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .width(screenWidth / 2f),
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
            ) {
                val items = 1..10
                items.forEach {
                    DropdownMenuItem(
                        text = { Text("History") },
                        onClick = { }
                    )
                }
            }

            IconButton(onClick = { /*TODO*/ }) {
                FaIcon(faIcon = FaIcons.Filter, size = 24.dp)
            }
        }
        CreateTransactionScreen(onNavigate = onNavigate)
    }
}