package com.costular.decorit.presentation.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.domain.interactor.*
import com.costular.decorit.domain.model.PhotoQuality
import com.costular.decorit.domain.model.Theme
import com.costular.decorit.presentation.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getThemeAvailableThemesInteractor: GetAvailableThemesInteractor,
    private val getSelectedThemeInteractor: GetSelectedThemeInteractor,
    private val selectThemeInteractor: SelectThemeInteractor,
    private val getViewPhotoQualityInteractor: GetViewPhotoQualityInteractor,
    private val getDownloadPhotoQualityInteractor: GetDownloadPhotoQualityInteractor,
    private val setViewPhotoQualityInteractor: SetViewPhotoQualityInteractor,
    private val setDownloadQualityInteractor: SetDownloadQualityInteractor
) : MviViewModel<SettingsState>(SettingsState()) {

    fun selectTheme(theme: Theme) = viewModelScope.launch {
        selectThemeInteractor(SelectThemeInteractor.Params(theme))
            .flowOn(dispatcher.io)
            .collect { status ->
                when (status) {
                    is InvokeSuccess -> {
                        AppCompatDelegate.setDefaultNightMode(theme.themeAsNightMode())
                    }
                    is InvokeError -> {
                        // TODO: 1/6/21 show error
                    }
                }
            }
    }

    init {
        viewModelScope.launch {
            getThemeAvailableThemesInteractor(Unit)
                .flowOn(dispatcher.io)
                .catch { Timber.e(it) }
                .collect { availableThemes ->
                    setState { copy(availableThemes = availableThemes) }
                }
        }

        viewModelScope.launch {
            getSelectedThemeInteractor(Unit)
            getSelectedThemeInteractor.observe()
                .flowOn(dispatcher.io)
                .catch { Timber.e(it) }
                .collect { selectedTheme ->
                    setState { copy(selectedTheme = selectedTheme) }
                }
        }

        viewModelScope.launch {
            getViewPhotoQualityInteractor(Unit)
            getViewPhotoQualityInteractor.observe()
                .flowOn(dispatcher.io)
                .catch { Timber.e(it) }
                .collect { quality ->
                    setState { copy(viewQuality = quality) }
                }
        }

        viewModelScope.launch {
            getDownloadPhotoQualityInteractor(Unit)
            getDownloadPhotoQualityInteractor.observe()
                .flowOn(dispatcher.io)
                .catch { Timber.e(it) }
                .collect { quality ->
                    setState { copy(downloadQuality = quality) }
                }
        }
    }

    fun selectViewQuality(photoQuality: PhotoQuality) = viewModelScope.launch {
        setViewPhotoQualityInteractor(SetViewPhotoQualityInteractor.Params(photoQuality))
            .flowOn(dispatcher.io)
            .collect { status ->
                when (status) {
                    is InvokeError -> {
                        // TODO: 2/6/21
                    }
                }
            }
    }

    fun selectDownloadQuality(downloadQuality: PhotoQuality) = viewModelScope.launch {
        setDownloadQualityInteractor(SetDownloadQualityInteractor.Params(downloadQuality))
            .flowOn(dispatcher.io)
            .collect { status ->
                when (status) {
                    is InvokeError -> {
                        // TODO: 2/6/21
                    }
                }
            }
    }

    private fun Theme.themeAsNightMode(): Int = when (this) {
        Theme.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        Theme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        Theme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
    }

}