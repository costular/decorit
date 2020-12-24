package com.costular.decorit.presentation.photos

import androidx.hilt.lifecycle.ViewModelInject
import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams
import com.costular.decorit.presentation.base.ReduxViewModel
import io.uniflow.core.flow.actionOn
import kotlinx.coroutines.flow.*
import timber.log.Timber

class PhotosViewModel @ViewModelInject constructor(
    private val dispatcher: DispatcherProvider,
    private val getPhotosInteractor: GetPhotosInteractor
) : ReduxViewModel(dispatcher, PhotosState()) {

    fun load() = actionOn<PhotosState> { state ->
        getPhotosInteractor.invoke(
            GetPhotosInteractor.Params(
                state.page,
                PER_PAGE,
                SearchParams()
            )
        )
            .catch { Timber.e(it) }
            .onStart { setState(state.copy(loadingMore = true)) }
            .collect {
                setState(
                    state.copy(
                        photos = state.photos + it,
                        page = state.page + 1,
                        loadingMore = false
                    )
                )
            }
    }

    fun openPhoto(photo: Photo) = action {
        sendEvent(PhotosEvents.OpenPhoto(photo))
    }

    companion object {
        private const val PER_PAGE = 20
    }

}