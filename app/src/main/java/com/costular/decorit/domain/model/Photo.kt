package com.costular.decorit.domain.model

import com.costular.decorit.util.PhotoId
import com.costular.decorit.util.SourceId

data class Photo(
    val id: PhotoId,
    val width: Int,
    val height: Int,
    val sourceId: SourceId,
    val photographer: Photographer,
    val isFavorite: Boolean,
    val original: String,
    val large: String,
    val medium: String,
    val small: String
)