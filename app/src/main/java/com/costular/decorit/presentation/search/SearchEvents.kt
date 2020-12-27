package com.costular.decorit.presentation.search

import com.costular.decorit.domain.model.Photo
import io.uniflow.core.flow.data.UIEvent

sealed class SearchEvents : UIEvent() {

    object OpenFilters : SearchEvents()

    data class OpenPhoto(val photo: Photo) : SearchEvents()

}
