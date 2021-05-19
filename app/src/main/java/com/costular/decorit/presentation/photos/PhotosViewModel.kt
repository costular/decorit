package com.costular.decorit.presentation.photos

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.interactor.GetViewPhotoQualityInteractor
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams
import com.costular.decorit.presentation.base.MviViewModel
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getPhotosInteractor: GetPhotosInteractor,
    private val getViewPhotoQualityInteractor: GetViewPhotoQualityInteractor
) : MviViewModel<PhotosState>(PhotosState()) {

    private val PER_PAGE = 20

    init {
        viewModelScope.launch {
            getViewPhotoQualityInteractor(Unit)

            getViewPhotoQualityInteractor.observe()
                .flowOn(dispatcher.io)
                .catch { Timber.e(it) }
                .collectAndSetState {
                    copy(viewQuality = it)
                }
        }

        load()
    }

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
            .flowOn(dispatcher.io)
            .catch { Timber.e(it) }
            .onStart {
                setState { copy(loadingMore = true) }
            }
            .collect { photos ->
                setState {
                    copy(
                        photos = this.photos + photos,
                        page = this.page + 1,
                        loadingMore = false
                    )
                }
            }
    }

    fun openPhoto(photo: Photo) {
        sendEvent(PhotosEvents.OpenPhoto(photo))
    }

}