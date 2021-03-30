package com.costular.decorit.presentation.search

import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.di.AssistedViewModelFactory
import com.costular.decorit.di.DaggerMvRxViewModelFactory
import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.model.*
import com.costular.decorit.presentation.base.MviViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus
import timber.log.Timber

class SearchViewModel @AssistedInject constructor(
    @Assisted state: SearchState,
    private val dispatcher: DispatcherProvider,
    private val getPhotosInteractor: GetPhotosInteractor
) : MviViewModel<SearchState>(state) {

    private val PER_PAGE = 20

    init {
        setState { copy(filterColors = calculateColors(state.params.color)) }
    }

    fun search(query: String = "", loadNext: Boolean = false) = withState { state ->
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
            .onStart { setState { copy(isLoading = true) } }
            .catch { Timber.e(it) }
            .onEach { photos ->
                setState {
                    val items = if (!loadNext) photos else items + photos

                    copy(
                        items = items,
                        page = page,
                        isLoading = false,
                        params = params,
                        showFilters = params.areEmpty().not()
                    )
                }
            }.launchIn(viewModelScope + dispatcher.io)
    }

    fun openFilter() {
        sendEvent(SearchEvents.OpenFilters)
    }

    fun openPhoto(photo: Photo) {
        sendEvent(SearchEvents.OpenPhoto(photo))
    }

    fun selectOrientation(orientation: PhotoOrientation) = withState { state ->
        setState { copy(params = params.copy(orientation = orientation)) }
        search(state.params.query)
    }

    fun selectColor(color: PhotoColor) = withState { state ->
        setState {
            copy(
                params = params.copy(color = color),
                filterColors = calculateColors(color)
            )
        }
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

    @AssistedInject.Factory
    interface Factory : AssistedViewModelFactory<SearchViewModel, SearchState> {
        override fun create(state: SearchState): SearchViewModel
    }

    companion object :
        DaggerMvRxViewModelFactory<SearchViewModel, SearchState>(SearchViewModel::class.java)


}