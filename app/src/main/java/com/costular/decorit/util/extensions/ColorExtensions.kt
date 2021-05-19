package com.costular.decorit.util.extensions

import android.graphics.Color

fun Int.isColorDark(): Boolean {
    val darkness: Double =
        1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
    return darkness >= 0.5
}