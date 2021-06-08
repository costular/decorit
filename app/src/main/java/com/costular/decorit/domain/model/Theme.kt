package com.costular.decorit.domain.model

import androidx.appcompat.app.AppCompatDelegate

enum class Theme(val key: String) {
    LIGHT("light"),
    DARK("dark"),
    SYSTEM("system");
}

fun themeFromStorageKey(storageKey: String): Theme? {
    return Theme.values().firstOrNull { it.key == storageKey }
}

fun Theme.themeAsNightMode(): Int = when (this) {
    Theme.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    Theme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
    Theme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
}