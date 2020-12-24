package com.costular.decorit.data.repository

import com.costular.decorit.data.unsplash.UnsplashPhotoDataSource
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams
import com.costular.decorit.domain.repository.PhotoRepository

class DefaultPhotoRepository(
    private val unsplashPhotoDataSource: UnsplashPhotoDataSource
) : PhotoRepository {

    override suspend fun getPhotos(
        page: Int,
        perPage: Int,
        searchParams: SearchParams
    ): List<Photo> {
        return mergeResults(
            unsplashPhotoDataSource.getPhotos(page, perPage, searchParams)
        )
    }

    override suspend fun getPhotoById(photoId: String): Photo {
        TODO("Not yet implemented")
    }

    override suspend fun getFavorites(): List<Photo> {
        TODO("Not yet implemented")
    }

    private fun mergeResults(vararg photos: List<Photo>): List<Photo> =
        ArrayList<Photo>().apply {
            photos.forEach { photoSource ->
                addAll(photoSource)
            }
        }

}