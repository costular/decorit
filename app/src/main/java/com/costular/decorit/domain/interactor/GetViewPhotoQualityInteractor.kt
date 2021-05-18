package com.costular.decorit.domain.interactor

import com.costular.decorit.domain.model.PhotoQuality
import com.costular.decorit.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetViewPhotoQualityInteractor(
    private val settingsRepository: SettingsRepository
) : SubjectInteractor<Unit, PhotoQuality>() {

    override fun createObservable(params: Unit): Flow<PhotoQuality> {
        return settingsRepository.getViewQuality()
    }

}