package com.costular.decorit.data.repository.settings

import android.content.Context
import android.os.Build
import androidx.core.os.BuildCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.costular.decorit.domain.model.PhotoQuality
import com.costular.decorit.domain.model.Theme
import com.costular.decorit.domain.model.themeFromStorageKey
import com.costular.decorit.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    override fun getTheme(): Flow<Theme> {
        val default = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> Theme.SYSTEM.key
            else -> Theme.DARK.key
        }
        return context.dataStore.data.map { preferences ->
            val themeKey = preferences[ThemeSetting] ?: default
            themeFromStorageKey(themeKey) ?: Theme.DARK
        }
    }

    override suspend fun setTheme(theme: Theme) {
        context.dataStore.edit { preferences ->
            preferences[ThemeSetting] = theme.key
        }
    }

    override fun getAvailableThemes(): List<Theme> {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> listOf(
                Theme.DARK,
                Theme.LIGHT,
                Theme.SYSTEM
            )
            else -> listOf(Theme.DARK, Theme.LIGHT)
        }
    }

    override fun getViewQuality(): Flow<PhotoQuality> {
        return context.dataStore.data.map { preferences ->
            val photoQuality = PhotoQuality.fromString(preferences[ViewQuality] ?: "medium")
            photoQuality
        }
    }

    override suspend fun setViewQuality(quality: PhotoQuality) {
        context.dataStore.edit { preferences ->
            preferences[ViewQuality] = quality.asString()
        }
    }

    override fun getDownloadQuality(): Flow<PhotoQuality> {
        return context.dataStore.data.map { preferences ->
            val photoQuality = PhotoQuality.fromString(preferences[DownloadQuality] ?: "medium")
            photoQuality
        }
    }

    override suspend fun setDownloadQuality(quality: PhotoQuality) {
        context.dataStore.edit { preferences ->
            preferences[DownloadQuality] = quality.asString()
        }
    }

    companion object {
        const val USER_PREFERENCES_NAME = "user_preferences"
        val ThemeSetting = stringPreferencesKey("theme")
        val ViewQuality = stringPreferencesKey("quality_view")
        val DownloadQuality = stringPreferencesKey("quality_download")
    }

}