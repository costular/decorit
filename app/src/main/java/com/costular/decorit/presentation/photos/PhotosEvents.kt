package com.costular.decorit.presentation.photos

import com.costular.decorit.domain.model.Photo
import com.costular.decorit.presentation.base.UiEvent

sealed class PhotosEvents : UiEvent {

    data class OpenPhoto(val photo: Photo) : PhotosEvents()

}