package com.costular.decorit.data.repository.settings

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.costular.decorit.domain.model.PhotoQuality
import com.costular.decorit.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    override fun getViewQuality(): Flow<PhotoQuality> {
        return context.dataStore.data.map { preferences ->
            val photoQuality = PhotoQuality.fromString(preferences[ViewQuality] ?: "medium")
            photoQuality
        }
    }

    override fun getDownloadQuality(): Flow<PhotoQuality> {
        return context.dataStore.data.map { preferences ->
            val photoQuality = PhotoQuality.fromString(preferences[DownloadQuality] ?: "medium")
            photoQuality
        }
    }

    companion object {
        const val USER_PREFERENCES_NAME = "user_preferences"
        val ViewQuality = stringPreferencesKey("quality_view")
        val DownloadQuality = stringPreferencesKey("quality_download")
    }

}