package com.costular.decorit.domain.repository

import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.PhotoColor
import com.costular.decorit.domain.model.SearchParams

interface PhotoRepository {

    suspend fun getPhotos(
        page: Int,
        perPage: Int,
        searchParams: SearchParams = SearchParams()
    ): List<Photo>

    suspend fun getPhotoById(photoId: String): Photo
    suspend fun getFavorites(): List<Photo>

}