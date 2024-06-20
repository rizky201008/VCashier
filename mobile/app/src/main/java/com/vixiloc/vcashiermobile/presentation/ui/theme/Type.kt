package com.vixiloc.vcashiermobile.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.vixiloc.vcashiermobile.R

val MontserratRegular = Font(R.font.montserrat)

private val defaultTypography = Typography()

val Type = Typography(
    bodySmall = TextStyle(
        fontFamily = FontFamily(MontserratRegular),
        fontSize = defaultTypography.bodySmall.fontSize,
        fontWeight = FontWeight.Normal,
        letterSpacing = defaultTypography.bodySmall.letterSpacing,
        lineHeight = defaultTypography.bodySmall.lineHeight,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(MontserratRegular),
        fontSize = defaultTypography.bodyMedium.fontSize,
        fontWeight = defaultTypography.bodyMedium.fontWeight,
        letterSpacing = defaultTypography.bodyMedium.letterSpacing,
        lineHeight = defaultTypography.bodyMedium.lineHeight,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(MontserratRegular),
        fontSize = defaultTypography.bodyLarge.fontSize,
        fontWeight = defaultTypography.bodyLarge.fontWeight,
        letterSpacing = defaultTypography.bodyLarge.letterSpacing,
        lineHeight = defaultTypography.bodyLarge.lineHeight,
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(MontserratRegular),
        fontSize = defaultTypography.titleSmall.fontSize,
        fontWeight = defaultTypography.titleSmall.fontWeight,
        letterSpacing = defaultTypography.titleSmall.letterSpacing,
        lineHeight = defaultTypography.titleSmall.lineHeight,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(MontserratRegular),
        fontSize = defaultTypography.titleMedium.fontSize,
        fontWeight = defaultTypography.titleMedium.fontWeight,
        letterSpacing = defaultTypography.titleMedium.letterSpacing,
        lineHeight = defaultTypography.titleMedium.lineHeight
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(MontserratRegular),
        fontSize = defaultTypography.titleLarge.fontSize,
        fontWeight = defaultTypography.titleLarge.fontWeight,
        letterSpacing = defaultTypography.titleLarge.letterSpacing,
        lineHeight = defaultTypography.titleLarge.lineHeight
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(MontserratRegular),
        fontSize = defaultTypography.headlineSmall.fontSize,
        fontWeight = defaultTypography.headlineSmall.fontWeight,
        letterSpacing = defaultTypography.headlineSmall.letterSpacing,
        lineHeight = defaultTypography.headlineSmall.lineHeight
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(MontserratRegular),
        fontSize = defaultTypography.headlineMedium.fontSize,
        fontWeight = defaultTypography.headlineMedium.fontWeight,
        letterSpacing = defaultTypography.headlineMedium.letterSpacing,
        lineHeight = defaultTypography.headlineMedium.lineHeight
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily(MontserratRegular),
        fontSize = defaultTypography.headlineLarge.fontSize,
        fontWeight = defaultTypography.headlineLarge.fontWeight,
        letterSpacing = defaultTypography.headlineLarge.letterSpacing,
        lineHeight = defaultTypography.headlineLarge.lineHeight
    )
)