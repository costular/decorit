package com.costular.decorit.domain.repository

import com.costular.decorit.domain.model.PhotoQuality
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getViewQuality(): Flow<PhotoQuality>
    fun getDownloadQuality(): Flow<PhotoQuality>

}