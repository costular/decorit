package com.costular.decorit.presentation.photos

import com.costular.decorit.domain.model.Photo
import io.uniflow.core.flow.data.UIState

data class PhotosState(
    val page: Int = 1,
    val photos: List<Photo> = emptyList()
) : UIState()