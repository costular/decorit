package com.costular.decorit.data.repository

import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams

interface PhotoDataSource {

    fun getSourceId(): String
    suspend fun getPhotos(page: Int, perPage: Int, searchParams: SearchParams = SearchParams()): List<Photo>
    suspend fun getPhotoById(photoId: String): Photo
    suspend fun getFavorites(): List<Photo>

}