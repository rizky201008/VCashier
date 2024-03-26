package com.vixiloc.vcashiermobile.presentation.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vixiloc.vcashiermobile.R
import com.vixiloc.vcashiermobile.presentation.widgets.utils.HorizontalLogo
import com.vixiloc.vcashiermobile.presentation.widgets.utils.VButton
import com.vixiloc.vcashiermobile.presentation.widgets.utils.VerticalSpacer

@Composable
fun WelcomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalLogo()
        VerticalSpacer(height = 120.dp)
        Image(
            painter = painterResource(id = R.drawable.img_welcome),
            contentDescription = null,
            modifier = Modifier
                .width(256.dp)
                .height(284.dp)
        )
        VerticalSpacer(height = 99.dp)
        Text(text = "Selamat datang", style = MaterialTheme.typography.bodyMedium)
        VerticalSpacer(height = 10.dp)
        VButton(text = "Login", onClick = { /*TODO*/ }, modifier = Modifier)
    }
}