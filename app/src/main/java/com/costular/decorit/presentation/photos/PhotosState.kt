package com.costular.decorit.presentation.photos

import com.costular.decorit.domain.model.Photo

data class PhotosState(
    val page: Int = 1,
    val photos: List<Photo> = emptyList(),
    val loadingMore: Boolean = false
)