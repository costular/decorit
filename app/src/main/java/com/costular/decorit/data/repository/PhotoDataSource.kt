package com.costular.decorit.data.repository

import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams
import com.costular.decorit.domain.model.Source
import com.costular.decorit.util.PhotoId
import com.costular.decorit.util.SourceId

interface PhotoDataSource {

    fun getSourceId(): SourceId
    suspend fun getPhotos(page: Int, perPage: Int, searchParams: SearchParams = SearchParams()): List<Photo>
    suspend fun getPhotoById(photoId: PhotoId): Photo
    suspend fun getFavorites(): List<Photo>

}