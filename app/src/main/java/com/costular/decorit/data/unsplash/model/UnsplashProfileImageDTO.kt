package com.costular.decorit.data.unsplash

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashProfileImageDTO(
    @field:Json(name = "small") val small: String,
    @field:Json(name = "medium") val medium: String,
    @field:Json(name = "large") val large: String
)