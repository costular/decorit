package com.costular.decorit.presentation.photos

import androidx.lifecycle.viewModelScope
import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams
import com.costular.decorit.presentation.base.MviViewModel
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getPhotosInteractor: GetPhotosInteractor
) : MviViewModel<PhotosState>(PhotosState()) {

    private val PER_PAGE = 20

    fun load() = viewModelScope.withState { state ->
        loadPage(state.page)
    }

    private fun loadPage(page: Int) = viewModelScope.launch {
        getPhotosInteractor.invoke(
            GetPhotosInteractor.Params(
                page,
                PER_PAGE,
                SearchParams()
            )
        )
            .catch { Timber.e(it) }
            .onStart {
                setState { copy(loadingMore = true) }
            }
            .collectAndSetState {
                copy(
                    photos = this.photos + photos,
                    page = this.page + 1,
                    loadingMore = false
                )
            }
    }

    fun openPhoto(photo: Photo) {
        sendEvent(PhotosEvents.OpenPhoto(photo))
    }

}