package com.costular.decorit.domain.interactor

import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.repository.PhotoRepository

class GetPhotoByIdInteractor(
    private val photoRepository: PhotoRepository
) : ResultInteractor<GetPhotoByIdInteractor.Params, Photo>() {

    data class Params(val photoId: String)

    override suspend fun doWork(params: Params): Photo {
        return photoRepository.getPhotoById(params.photoId)
    }

}