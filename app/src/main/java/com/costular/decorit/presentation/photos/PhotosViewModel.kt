package com.costular.decorit.presentation.photos

import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.di.AssistedViewModelFactory
import com.costular.decorit.di.DaggerMvRxViewModelFactory
import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams
import com.costular.decorit.presentation.base.MviViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.plus
import timber.log.Timber

class PhotosViewModel @AssistedInject constructor(
    @Assisted state: PhotosState,
    private val dispatcher: DispatcherProvider,
    private val getPhotosInteractor: GetPhotosInteractor
) : MviViewModel<PhotosState>(state) {

    private val PER_PAGE = 20

    fun load() = withState { state ->
        getPhotosInteractor.invoke(
            GetPhotosInteractor.Params(
                state.page,
                PER_PAGE,
                SearchParams()
            )
        )
            .catch { Timber.e(it) }
            .onStart {
                setState { copy(loadingMore = true) }
            }
            .onEach { photos ->
                setState {
                    copy(
                        photos = this.photos + photos,
                        page = this.page + 1,
                        loadingMore = false
                    )
                }
            }
            .launchIn(viewModelScope + dispatcher.io)
    }

    fun openPhoto(photo: Photo) {
        sendEvent(PhotosEvents.OpenPhoto(photo))
    }

    @AssistedInject.Factory
    interface Factory : AssistedViewModelFactory<PhotosViewModel, PhotosState> {
        override fun create(state: PhotosState): PhotosViewModel
    }

    companion object :
        DaggerMvRxViewModelFactory<PhotosViewModel, PhotosState>(PhotosViewModel::class.java)

}