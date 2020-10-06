package com.costular.decorit.domain.model

import com.costular.decorit.util.PhotoColor

data class SearchParams(
    val color: List<PhotoColor> = emptyList(),
    val orientation: PhotoOrientation = PhotoOrientation.ANY
)

fun SearchParams.areEmpty() = (this.color.isEmpty() && orientation == PhotoOrientation.ANY)