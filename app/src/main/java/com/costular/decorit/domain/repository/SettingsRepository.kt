package com.costular.decorit.domain.repository

import com.costular.decorit.domain.model.PhotoQuality
import com.costular.decorit.domain.model.Theme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getTheme(): Flow<Theme>
    suspend fun setTheme(theme: Theme)
    fun getAvailableThemes(): List<Theme>

    fun getViewQuality(): Flow<PhotoQuality>
    suspend fun setViewQuality(quality: PhotoQuality)

    fun getDownloadQuality(): Flow<PhotoQuality>
    suspend fun setDownloadQuality(quality: PhotoQuality)

}