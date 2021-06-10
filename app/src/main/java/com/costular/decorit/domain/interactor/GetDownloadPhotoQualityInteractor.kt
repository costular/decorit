package com.costular.decorit.domain.interactor

import com.costular.decorit.domain.model.PhotoQuality
import com.costular.decorit.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDownloadPhotoQualityInteractor @Inject constructor(
    private val settingsRepository: SettingsRepository
) : SubjectInteractor<Unit, PhotoQuality>() {

    override fun createObservable(params: Unit): Flow<PhotoQuality> =
        settingsRepository.getDownloadQuality()

}