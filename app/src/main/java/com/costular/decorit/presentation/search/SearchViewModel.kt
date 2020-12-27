package com.costular.decorit.presentation.search

import androidx.hilt.lifecycle.ViewModelInject
import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.model.*
import com.costular.decorit.presentation.base.ReduxViewModel
import io.uniflow.core.flow.actionOn
import kotlinx.coroutines.flow.*
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
        val params = if (!loadNext) {
            if (query.isNotEmpty()) {
                state.params.copy(query = query)
            } else {
                SearchParams(query = query)
            }
        } else {
            state.params
        }
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
                        params = params,
                        showFilters = params.areEmpty().not()
                    )
                )
            }
    }

    fun openFilter() = action {
        sendEvent(SearchEvents.OpenFilters)
    }

    fun openPhoto(photo: Photo) = action {
        sendEvent(SearchEvents.OpenPhoto(photo))
    }

    fun selectOrientation(orientation: PhotoOrientation) = actionOn<SearchState> { state ->
        setState(state.copy(params = state.params.copy(orientation = orientation)))
        search(state.params.query)
    }

    fun selectColor(color: PhotoColor) = actionOn<SearchState> { state ->
        setState(
            state.copy(
                params = state.params.copy(color = color),
                filterColors = calculateColors(color)
            )
        )
        search(state.params.query)
    }

    private fun calculateColors(colorSelected: PhotoColor?): List<ColorFilterItem> =
        ColorValue.values()
            .map { color ->
                ColorFilterItem(
                    color,
                    colorSelected?.value == color
                )
            }

    companion object {
        private const val PER_PAGE = 20
    }

}