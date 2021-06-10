package com.costular.decorit.domain.interactor

import com.costular.decorit.domain.model.PhotoQuality
import com.costular.decorit.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetViewPhotoQualityInteractor @Inject constructor(
    private val settingsRepository: SettingsRepository
) : Interactor<SetViewPhotoQualityInteractor.Params>() {

    data class Params(val quality: PhotoQuality)

    override suspend fun doWork(params: Params) {
        settingsRepository.setViewQuality(params.quality)
    }

}