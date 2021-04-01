package com.costular.decorit.presentation.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkColorPalette = darkColors(
    primary = teal200,
    onPrimary = Color.Black,
    secondary = teal200,
    onSecondary = Color.Black
).withBrandedSurface()

val LightColorPalette = lightColors(
    primary = teal500,
    onPrimary = Color.White,
    secondary = teal500,
    onSecondary = Color.White
)

@Composable
fun DecoritTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}