package com.costular.decorit.data.unsplash

import com.costular.decorit.data.repository.photos.PhotoDataSource
import com.costular.decorit.data.unsplash.model.toPhoto
import com.costular.decorit.domain.model.*

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
            // Search instead
            return search(page, perPage, searchParams)
        }
    }

    private suspend fun search(page: Int, perPage: Int, params: SearchParams): List<Photo> {
        val query = if (params.query.isNotEmpty()) params.query else null
        val color = if (params.color != null) params.color.toUnsplashColor() else null
        val orientation =
            if (params.orientation != null) params.orientation.toUnsplashOrientation() else null

        return unsplashApi.searchPhotos(page, perPage, query, color, orientation)
            .results
            .map { it.toPhoto() }
    }

    override suspend fun getPhotoById(photoId: String): Photo =
        unsplashApi.getPhotoById(photoId).toPhoto()

    override suspend fun getFavorites(): List<Photo> {
        TODO("Not yet implemented")
    }

}