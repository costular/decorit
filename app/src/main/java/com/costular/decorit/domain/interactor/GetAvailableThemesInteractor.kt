package com.costular.decorit.domain.interactor

import com.costular.decorit.domain.model.Theme
import com.costular.decorit.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAvailableThemesInteractor @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ResultInteractor<Unit, List<Theme>>() {

    override suspend fun doWork(params: Unit): List<Theme> {
        return settingsRepository.getAvailableThemes()
    }

}