package com.costular.decorit.data.unsplash

import com.costular.decorit.data.repository.PhotoDataSource
import com.costular.decorit.data.unsplash.model.toPhoto
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams
import com.costular.decorit.domain.model.areEmpty
import com.costular.decorit.util.PhotoId
import com.costular.decorit.util.SourceId

class UnsplashPhotoDataSource(private val unsplashApi: UnsplashApi) : PhotoDataSource {

    override fun getSourceId(): SourceId = SourceId("unsplash")

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

    override suspend fun getPhotoById(photoId: PhotoId): Photo =
        unsplashApi.getPhotoById(photoId.id).toPhoto()

    override suspend fun getFavorites(): List<Photo> {
        TODO("Not yet implemented")
    }

}