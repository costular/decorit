package com.costular.decorit.domain.interactor

import com.costular.decorit.domain.model.Theme
import com.costular.decorit.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSelectedThemeInteractor @Inject constructor(
    private val settingsRepository: SettingsRepository
) : SubjectInteractor<Unit, Theme>() {

    override fun createObservable(params: Unit): Flow<Theme> {
        return settingsRepository.getTheme()
    }
}