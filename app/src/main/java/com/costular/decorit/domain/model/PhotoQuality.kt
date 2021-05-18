package com.costular.decorit.domain.model

enum class PhotoQuality {
    Low, Medium, High, Full;

    fun asString(): String = when (this) {
        Low -> "low"
        Medium -> "medium"
        High -> "high"
        Full -> "full"
        else -> "medium"
    }

    companion object {
        fun fromString(string: String): PhotoQuality = when (string) {
            "low" -> Low
            "medium" -> Medium
            "high" -> High
            "full" -> Full
            else -> Medium
        }
    }
}