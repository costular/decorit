package com.costular.decorit.presentation.photodetail

import com.costular.decorit.presentation.base.UiEvent

sealed class PhotoDetailEvents : UiEvent {

    data class SetAsWallpaper(val uri: String) : PhotoDetailEvents()

    object DownloadedSuccessfully : PhotoDetailEvents()

}