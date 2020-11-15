package com.costular.decorit.presentation.photodetail

import io.uniflow.core.flow.data.UIEvent

sealed class PhotoDetailEvents : UIEvent() {

    data class LoadPhoto(val url: String) : PhotoDetailEvents()

}