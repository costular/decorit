package com.costular.decorit.data.unsplash

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashUserDTO(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "profile_image") val profileImageDTO: UnsplashProfileImageDTO
)