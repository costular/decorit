package com.costular.decorit.presentation.photodetail

import com.costular.decorit.domain.Async
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.PhotoQuality

data class PhotoDetailState(
    val photoId: String? = null,
    val photo: Async<Photo> = Async.Uninitialized,
    val isDownloading: Boolean = false,
    val isSettingAsWallpaper: Boolean = false,
    val downloadQuality: PhotoQuality = PhotoQuality.High
)