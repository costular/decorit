package com.costular.decorit.presentation.search

import com.costular.decorit.domain.model.PhotoColor

data class ColorFilterItem(
    val color: PhotoColor,
    val isSelected: Boolean = false
)