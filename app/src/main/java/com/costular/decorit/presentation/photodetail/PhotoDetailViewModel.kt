package com.costular.decorit.presentation.photodetail

import androidx.hilt.lifecycle.ViewModelInject
import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.presentation.base.ReduxViewModel
import io.uniflow.core.flow.actionOn

class PhotoDetailViewModel @ViewModelInject constructor(
    private val dispatcher: DispatcherProvider
) : ReduxViewModel(dispatcher, PhotoDetailState()) {

    fun loadPhoto(photo: Photo) = actionOn<PhotoDetailState> { state ->
        setState(state.copy(photo = photo))
        sendEvent(PhotoDetailEvents.LoadPhoto(photo.large))
    }

    fun photoLoadedSuccessfully() = actionOn<PhotoDetailState> { state ->
        setState(state.copy(isLoading = false))
    }

}