package com.costular.decorit.presentation.search

import com.costular.decorit.domain.model.ColorValue

data class ColorFilterItem(
    val color: ColorValue,
    val isSelected: Boolean = false
)