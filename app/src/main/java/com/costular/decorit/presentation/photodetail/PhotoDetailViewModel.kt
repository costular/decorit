package com.costular.decorit.presentation.photodetail

import androidx.lifecycle.viewModelScope
import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.domain.Async
import com.costular.decorit.domain.interactor.GetPhotoByIdInteractor
import com.costular.decorit.presentation.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getPhotoByIdInteractor: GetPhotoByIdInteractor
) : MviViewModel<PhotoDetailState>(PhotoDetailState()) {

    fun load(photoId: String) = viewModelScope.launch(dispatcher.main) {
        setState { copy(photoId = photoId) }

        getPhotoByIdInteractor(GetPhotoByIdInteractor.Params(photoId))
            .flowOn(dispatcher.io)
            .catch { setState { copy(photo = Async.Failure(it)) } }
            .onStart { setState { copy(photo = Async.Loading) } }
            .onEach { photo -> setState { copy(photo = Async.Success(photo)) } }
            .launchIn(this)
    }

}