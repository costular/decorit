package com.costular.decorit.util.initializers

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.di.AppScope
import com.costular.decorit.domain.model.themeAsNightMode
import com.costular.decorit.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ThemeInitializer @Inject constructor(
    @AppScope private val appScope: CoroutineScope,
    private val dispatcher: DispatcherProvider,
    private val settingsRepository: SettingsRepository
) : AppInitializer {

    override fun init(application: Application) {
        appScope.launch {
            val theme = withContext(dispatcher.io) {
                settingsRepository.getTheme().take(1).toList().first()
            }
            AppCompatDelegate.setDefaultNightMode(theme.themeAsNightMode())
        }
    }

}