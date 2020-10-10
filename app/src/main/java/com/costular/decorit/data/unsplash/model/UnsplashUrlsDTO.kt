package com.costular.decorit.data.unsplash.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashUrlsDTO(
    @field:Json(name = "raw") val original: String,
    @field:Json(name = "full") val full: String,
    @field:Json(name = "regular") val regular: String,
    @field:Json(name = "small") val small: String
)