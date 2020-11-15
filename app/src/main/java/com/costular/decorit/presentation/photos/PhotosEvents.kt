package com.costular.decorit.presentation.photos

import com.costular.decorit.domain.model.Photo
import io.uniflow.core.flow.data.UIEvent

sealed class PhotosEvents : UIEvent() {

    data class OpenPhoto(val photo: Photo) : PhotosEvents()

}