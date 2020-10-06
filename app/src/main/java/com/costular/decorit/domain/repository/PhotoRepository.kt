package com.costular.decorit.domain.repository

import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams
import com.costular.decorit.util.PhotoId

interface PhotoRepository {

    suspend fun getPhotos(page: Int, perPage: Int, searchParams: SearchParams = SearchParams()): List<Photo>
    suspend fun getPhotoById(photoId: PhotoId): Photo
    suspend fun getFavorites(): List<Photo>

}