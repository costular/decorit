package com.costular.decorit.presentation.search

import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.PhotoQuality
import com.costular.decorit.domain.model.SearchParams

data class SearchState(
    val items: List<Photo> = emptyList(),
    val page: Int = 1,
    val isLoading: Boolean = false,
    val params: SearchParams = SearchParams(),
    val filterColors: List<ColorFilterItem> = emptyList(),
    val photoQuality: PhotoQuality = PhotoQuality.Medium,
    val showFilters: Boolean = false
)