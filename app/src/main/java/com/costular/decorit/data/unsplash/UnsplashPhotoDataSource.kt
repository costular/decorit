package com.costular.decorit.data.unsplash

import com.costular.decorit.data.repository.PhotoDataSource
import com.costular.decorit.data.unsplash.model.toPhoto
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams
import com.costular.decorit.domain.model.areEmpty

class UnsplashPhotoDataSource(private val unsplashApi: UnsplashApi) : PhotoDataSource {

    override fun getSourceId(): String = "unsplash"

    override suspend fun getPhotos(
        page: Int,
        perPage: Int,
        searchParams: SearchParams
    ): List<Photo> {
        return if (searchParams.areEmpty()) {
            // Get normal photos
            unsplashApi.getPhotos(page, perPage).map { it.toPhoto() }
        } else {
            // Use a different endpoint for searching
            unsplashApi.getPhotos(page, perPage).map { it.toPhoto() } // TODO CHANGE THIS TO THE SEARCH ENDPOINT
        }
    }

    override suspend fun getPhotoById(photoId: String): Photo =
        unsplashApi.getPhotoById(photoId).toPhoto()

    override suspend fun getFavorites(): List<Photo> {
        TODO("Not yet implemented")
    }

}