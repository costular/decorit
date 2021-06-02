package com.costular.decorit.domain.interactor

import com.costular.decorit.domain.model.Theme
import com.costular.decorit.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelectThemeInteractor @Inject constructor(
    private val settingsRepository: SettingsRepository
) : Interactor<SelectThemeInteractor.Params>() {

    data class Params(val theme: Theme)

    override suspend fun doWork(params: Params) {
        settingsRepository.setTheme(params.theme)
    }

}