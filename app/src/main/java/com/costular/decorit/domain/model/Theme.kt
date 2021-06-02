package com.costular.decorit.domain.model

enum class Theme(val key: String) {
    LIGHT("light"),
    DARK("dark"),
    SYSTEM("system");
}

fun themeFromStorageKey(storageKey: String): Theme? {
    return Theme.values().firstOrNull { it.key == storageKey }
}