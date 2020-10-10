package com.costular.decorit.domain.model

import com.costular.decorit.util.PhotographerId

data class Photographer(
    val id: PhotographerId,
    val name: String,
    val avatar: String?
)