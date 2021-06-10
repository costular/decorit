package com.costular.decorit.data.unsplash.model

import com.costular.decorit.data.unsplash.model.UnsplashProfileImageDTO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashUserDTO(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "profile_image") val profileImageDTO: UnsplashProfileImageDTO,
    @Json(name = "links") val links: UnsplashUserLinks
)