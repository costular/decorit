package com.costular.decorit.data.unsplash.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashUserLinks(
    @Json(name = "self") val self: String
)