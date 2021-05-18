package com.costular.decorit.data.repository.photos

import com.costular.decorit.data.SourceConstants
import com.costular.decorit.data.unsplash.UnsplashPhotoDataSource
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams
import com.costular.decorit.domain.repository.PhotoRepository
import java.lang.IllegalArgumentException

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
        val split = photoId.split("-")
        val source = split.first()
        val sourcePhotoId = split.last()
        return when (source) {
            SourceConstants.UNSPLASH -> unsplashPhotoDataSource.getPhotoById(sourcePhotoId)
            else -> throw IllegalArgumentException("Source not supported yet!")
        }
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