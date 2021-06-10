package com.costular.decorit.data.unsplash.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashPhotoDTO(
    @Json(name = "id") val id: String,
    @Json(name = "width") val width: Int,
    @Json(name = "height") val height: Int,
    @Json(name = "user") val userDTO: UnsplashUserDTO,
    @Json(name = "urls") val urlsDTO: UnsplashUrlsDTO
)