package com.costular.decorit.presentation.photodetail

import com.costular.decorit.domain.model.Photo
import io.uniflow.core.flow.data.UIState

data class PhotoDetailState(
    val photo: Photo? = null,
    val isLoading: Boolean = true
) : UIState()