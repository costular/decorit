package com.costular.decorit.presentation.photos

import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.PhotoQuality

data class PhotosState(
    val page: Int = 1,
    val photos: List<Photo> = emptyList(),
    val loadingMore: Boolean = false,
    val viewQuality: PhotoQuality = PhotoQuality.Medium
)