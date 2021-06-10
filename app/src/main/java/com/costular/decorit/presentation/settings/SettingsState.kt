package com.costular.decorit.presentation.settings

import com.costular.decorit.domain.model.PhotoQuality
import com.costular.decorit.domain.model.Theme

data class SettingsState(
    val availableThemes: List<Theme> = emptyList(),
    val selectedTheme: Theme? = null,
    val viewQuality: PhotoQuality = PhotoQuality.Medium,
    val downloadQuality: PhotoQuality = PhotoQuality.High,
    val availableQualities: List<PhotoQuality> = listOf(
        PhotoQuality.Low,
        PhotoQuality.Medium,
        PhotoQuality.High,
        PhotoQuality.Full
    )
)