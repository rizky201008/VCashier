package com.vixiloc.vcashiermobile.presentation.dashboard


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Selamat Datang Admin",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(10.dp)
        )
        LazyVerticalGrid(
            modifier = Modifier.height(350.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(6) {
                DashboardCard(modifier = Modifier.padding(5.dp))
            }
        }
    }
}

@Composable
fun DashboardCard(
    modifier: Modifier,
    icon: ImageVector = Icons.Default.Money,
    title: String = "Omzet Total",
    value: String = "Rp300.000.000"
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    Modifier.size(30.dp),
                    tint = Color.Black
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}