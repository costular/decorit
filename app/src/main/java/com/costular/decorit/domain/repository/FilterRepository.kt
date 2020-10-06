package com.costular.decorit.domain.repository

import com.costular.decorit.domain.model.PhotoColor
import com.costular.decorit.domain.model.PhotoOrientation

interface FilterRepository {

    fun getColors(): List<PhotoColor>
    fun getOrientation(): List<PhotoOrientation>

}