package com.vixiloc.vcashiermobile.presentation.widgets.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vixiloc.vcashiermobile.R

@Composable
fun HorizontalLogo() {
    Image(
        painter = painterResource(id = R.drawable.logo_miring),
        contentDescription = null,
        modifier = Modifier
            .width(155.dp)
            .height(50.dp)
    )
}