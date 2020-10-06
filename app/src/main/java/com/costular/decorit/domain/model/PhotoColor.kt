package com.costular.decorit.domain.model

data class PhotoColor(
    val colorInt: Int,
    val value: ColorValue
)

enum class ColorValue {
    BLACK_AND_WHITE, BLACK, WHITE, YELLOW, ORANGE, RED, PURPLE, MAGENTA, GREEN, TEAL, BLUE;
}