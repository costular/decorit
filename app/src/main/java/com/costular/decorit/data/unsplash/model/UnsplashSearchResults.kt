package com.costular.decorit.data.unsplash.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashSearchResults(
    @Json(name = "total") val total: Int,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "results") val results: List<UnsplashPhotoDTO>
)