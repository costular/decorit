package com.costular.decorit.presentation.photodetail

import androidx.lifecycle.viewModelScope
import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.domain.Async
import com.costular.decorit.domain.interactor.GetDownloadPhotoQualityInteractor
import com.costular.decorit.domain.interactor.GetPhotoByIdInteractor
import com.costular.decorit.presentation.base.MviViewModel
import com.costular.decorit.workers.DownloadWorker
import com.costular.decorit.workers.WorkerRunner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getPhotoByIdInteractor: GetPhotoByIdInteractor,
    private val getDownloadPhotoQualityInteractor: GetDownloadPhotoQualityInteractor,
    private val workerRunner: WorkerRunner
) : MviViewModel<PhotoDetailState>(PhotoDetailState()) {

    init {
        viewModelScope.launch {
            getDownloadPhotoQualityInteractor(Unit)
            getDownloadPhotoQualityInteractor.observe()
                .flowOn(dispatcher.io)
                .catch { Timber.e(it) }
                .collect { quality ->
                    setState { copy(downloadQuality = quality) }
                }
        }
    }

    fun load(photoId: String) = viewModelScope.launch(dispatcher.main) {
        setState { copy(photoId = photoId) }

        getPhotoByIdInteractor(GetPhotoByIdInteractor.Params(photoId))
            .flowOn(dispatcher.io)
            .catch { setState { copy(photo = Async.Failure(it)) } }
            .onStart { setState { copy(photo = Async.Loading) } }
            .onEach { photo -> setState { copy(photo = Async.Success(photo)) } }
            .launchIn(this)
    }

    fun download() = viewModelScope.withState { state ->
        val uuid = download(state)

        if (uuid != null) {
            viewModelScope.launch(dispatcher.io) {
                workerRunner.listenWorkerInfo(uuid)
                    .distinctUntilChanged()
                    .catch { Timber.e(it) }
                    .collect { workInfo ->
                        setState { copy(isDownloading = !workInfo.state.isFinished) }
                        if (workInfo.state.isFinished) {
                            sendEvent(PhotoDetailEvents.DownloadedSuccessfully)
                        }
                    }
            }
        }
    }

    fun setAsWallpaper() = viewModelScope.withState { state ->
        val uuid = download(state)

        if (uuid != null) {
            viewModelScope.launch(dispatcher.io) {
                workerRunner.listenWorkerInfo(uuid)
                    .distinctUntilChanged()
                    .catch { Timber.e(it) }
                    .collect { workInfo ->
                        setState { copy(isSettingAsWallpaper = !workInfo.state.isFinished) }

                        if (workInfo.state.isFinished) {
                            val uri = workInfo.outputData.getString(DownloadWorker.OutputUri)
                            if (uri != null) {
                                sendEvent(PhotoDetailEvents.DownloadedSuccessfully)
                                sendEvent(PhotoDetailEvents.SetAsWallpaper(uri))
                            }
                        }
                    }
            }
        }
    }

    private fun download(state: PhotoDetailState): UUID? {
        val photo = state.photo
        return if (photo is Async.Success) {
            val data = photo()
            workerRunner.enqueueDownloadWorker(
                data.photoUrlFromQuality(state.downloadQuality),
                data.photographer.name
            )
        } else {
            null
        }
    }

}