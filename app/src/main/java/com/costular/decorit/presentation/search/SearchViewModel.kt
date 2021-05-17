package com.costular.decorit.presentation.search

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.model.*
import com.costular.decorit.presentation.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getPhotosInteractor: GetPhotosInteractor
) : MviViewModel<SearchState>(SearchState()) {

    private val queryQueue: Channel<String> = Channel(capacity = 100)

    private val PER_PAGE = 20

    init {
        viewModelScope.launchSetState { copy(filterColors = calculateColors(params.color)) }

        viewModelScope.launch {
            queryQueue
                .consumeAsFlow()
                .distinctUntilChanged()
                .debounce(300L)
                .collect { query ->
                    search(query)
                }
        }
    }

    fun enqueueQuery(query: String) = viewModelScope.launch {
        queryQueue.send(query)
    }

    fun search(query: String = "", loadNext: Boolean = false) = viewModelScope.withState { state ->
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
            .flowOn(dispatcher.io)
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
            }.launchIn(viewModelScope)
    }

    fun openFilter() {
        sendEvent(SearchEvents.OpenFilters)
    }

    fun openPhoto(photo: Photo) {
        sendEvent(SearchEvents.OpenPhoto(photo))
    }

    fun selectOrientation(orientation: PhotoOrientation) {
        viewModelScope.launchSetState {
            copy(params = params.copy(orientation = orientation))
        }
        viewModelScope.withState { state ->
            search(state.params.query)
        }
    }

    fun selectColor(color: PhotoColor) {
        viewModelScope.launchSetState {
            copy(
                params = params.copy(color = color),
                filterColors = calculateColors(color)
            )
        }
        viewModelScope.withState { state -> search(state.params.query) }
    }

    private fun calculateColors(colorSelected: PhotoColor?): List<ColorFilterItem> =
        ColorValue.values()
            .map { color ->
                ColorFilterItem(
                    color,
                    colorSelected?.value == color
                )
            }

}