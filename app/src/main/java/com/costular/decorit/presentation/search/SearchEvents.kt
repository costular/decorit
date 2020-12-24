package com.costular.decorit.presentation.search

import io.uniflow.core.flow.data.UIEvent

sealed class SearchEvents : UIEvent() {

    object OpenFilters : SearchEvents()

}
