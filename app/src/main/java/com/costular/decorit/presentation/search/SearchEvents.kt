package com.costular.decorit.presentation.search

import com.costular.decorit.domain.model.Photo
import com.costular.decorit.presentation.base.UiEvent
import io.uniflow.core.flow.data.UIEvent

sealed class SearchEvents : UiEvent {

    object OpenFilters : SearchEvents()

    data class OpenPhoto(val photo: Photo) : SearchEvents()

}
