package com.vixiloc.vcashiermobile.presentation.widgets.commons

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun VerticalSpacer(height: Dp, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(height))
}

@Composable
fun HorizontalSpacer(width: Dp, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(width))
}