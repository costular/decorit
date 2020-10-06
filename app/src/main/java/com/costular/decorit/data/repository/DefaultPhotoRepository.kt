package com.costular.decorit.data.repository

import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams
import com.costular.decorit.domain.repository.PhotoRepository
import com.costular.decorit.util.PhotoId

class DefaultPhotoRepository : PhotoRepository {

    override suspend fun getPhotos(searchParams: SearchParams): List<Photo> {
        TODO("Not yet implemented")
    }

    override suspend fun getPhotoById(photoId: PhotoId): Photo {
        TODO("Not yet implemented")
    }

    override suspend fun getFavorites(): List<Photo> {
        TODO("Not yet implemented")
    }

}