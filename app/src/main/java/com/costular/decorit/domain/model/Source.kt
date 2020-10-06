package com.costular.decorit.domain.model

import com.costular.decorit.util.SourceId

data class Source(
    val id: SourceId,
    val name: String,
    val link: String,
    val iconDrawable: Int
)