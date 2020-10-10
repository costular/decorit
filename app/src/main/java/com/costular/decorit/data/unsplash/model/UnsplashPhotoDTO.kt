package com.costular.decorit.data.unsplash.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashPhotoDTO(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "width") val width: Int,
    @field:Json(name = "height") val height: Int,
    @field:Json(name = "user") val userDTO: UnsplashUserDTO,
    @field:Json(name = "urls") val urlsDTO: UnsplashUrlsDTO
)