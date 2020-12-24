package com.costular.decorit.presentation.search

import android.graphics.Color
import androidx.hilt.lifecycle.ViewModelInject
import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.model.ColorValue
import com.costular.decorit.domain.model.PhotoColor
import com.costular.decorit.domain.model.PhotoOrientation
import com.costular.decorit.presentation.base.ReduxViewModel
import io.uniflow.core.flow.actionOn
import io.uniflow.core.flow.data.UIState
import io.uniflow.core.flow.getCurrentStateOrNull
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

class SearchViewModel @ViewModelInject constructor(
    dispatcher: DispatcherProvider,
    private val getPhotosInteractor: GetPhotosInteractor
) : ReduxViewModel(dispatcher, SearchState()) {

    init {
        action {
            setState(SearchState(filterColors = calculateColors(null)))
        }
    }

    fun search(query: String = "", loadNext: Boolean = false) = actionOn<SearchState> { state ->
        val params = if (!loadNext) state.params.copy(query = query) else state.params
        val page = if (!loadNext) 1 else state.page + 1

        getPhotosInteractor(GetPhotosInteractor.Params(page, PER_PAGE, params))
            .onStart { setState(state.copy(isLoading = true)) }
            .catch { Timber.e(it) }
            .collect { photos ->
                val items = if (!loadNext) photos else state.items + photos

                setState(
                    state.copy(
                        items = items,
                        page = page,
                        isLoading = false,
                        params = params
                    )
                )
            }

    }

    fun openFilter() = action {
        sendEvent(SearchEvents.OpenFilters)
    }

    fun selectOrientation(orientation: PhotoOrientation) = actionOn<SearchState> { state ->
        setState(state.copy(params = state.params.copy(orientation = orientation)))
    }

    fun selectColor(color: PhotoColor) = actionOn<SearchState> { state ->
        setState(
            state.copy(
                params = state.params.copy(color = color),
                filterColors = calculateColors(color)
            )
        )
    }

    private fun calculateColors(colorSelected: PhotoColor?): List<ColorFilterItem> =
        ColorValue
            .values()
            .map { color ->
                ColorFilterItem(
                    PhotoColor(Color.CYAN, color),
                    colorSelected?.value == color
                )
            }

    companion object {
        private const val PER_PAGE = 20
    }

}