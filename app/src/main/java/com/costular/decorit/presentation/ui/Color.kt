package com.costular.decorit.presentation.ui

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val teal500 = Color(0xFF009688)
val teal200 = Color(0xFF80CBC4)
val teal700 = Color(0xFF00796B)
val black = Color(0xFF000000)
val white = Color(0xFFFFFFFF)
val placeholder = Color(0xFF80CBC4)

val systemUiScrimLight = 0xB3FFFFFF
val systemUiScrimDark = 0x30000000

val lightDivider = Color(0x1F000000)
val darkDivider = Color(0x1FFFFFFF)

val red500 = Color(0xFFf44336)
val red200 = Color(0xFFef9a9a)
val blue500 = Color(0xFF2196f3)
val blue200 = Color(0xFF90caf9)
val yellow500 = Color(0xFFffeb3b)
val yellow200 = Color(0xFFfff59d)
val orange500 = Color(0xFFff9800)
val orange200 = Color(0xFFffcc80)
val purple500 = Color(0xFF673ab7)
val purple200 = Color(0xFFb39ddb)
val magenta500 = Color(0xFF9c27b0)
val magenta200 = Color(0xFFce93d8)
val green500 = Color(0xFF4caf50)
val green200 = Color(0xFFa5d6a7)

fun Colors.withBrandedSurface() = copy(
    surface = primary.copy(alpha = 0.08f).compositeOver(this.surface),
)