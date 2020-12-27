package com.costular.decorit.domain.model


data class SearchParams(
    val query: String = "",
    val color: PhotoColor? = null,
    val orientation: PhotoOrientation = PhotoOrientation.ANY
)

fun SearchParams.areEmpty() =
    (this.query.isEmpty() && this.color == null && orientation == PhotoOrientation.ANY)

fun PhotoOrientation.toUnsplashOrientation(): String? =
    when (this) {
        PhotoOrientation.HORIZONTAL -> "landscape"
        PhotoOrientation.VERTICAL -> "portrait"
        PhotoOrientation.ANY -> null
    }

fun PhotoColor.toUnsplashColor(): String? =
    when (value) {
        ColorValue.BLACK -> "black"
        ColorValue.BLACK_AND_WHITE -> "black_and_white"
        ColorValue.BLUE -> "blue"
        ColorValue.GREEN -> "green"
        ColorValue.MAGENTA -> "magenta"
        ColorValue.ORANGE -> "orange"
        ColorValue.PURPLE -> "purple"
        ColorValue.RED -> "red"
        ColorValue.TEAL -> "teal"
        ColorValue.WHITE -> "white"
        ColorValue.YELLOW -> "yellow"
    }