package com.costular.decorit.presentation.search

import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams
import io.uniflow.core.flow.data.UIState

data class SearchState(
    val items: List<Photo> = emptyList(),
    val page: Int = 1,
    val isLoading: Boolean = false,
    val params: SearchParams = SearchParams(),
    val filterColors: List<ColorFilterItem> = emptyList(),
    val showFilters: Boolean = false
) : UIState()