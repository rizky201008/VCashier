package com.vixiloc.vcashiermobile.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vixiloc.vcashiermobile.R

val RegularPtSans = Font(R.font.ptsans_regular)

val Type = Typography(
    bodySmall = TextStyle(
        fontFamily = FontFamily(RegularPtSans),
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(RegularPtSans),
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(RegularPtSans),
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal
    ),
)