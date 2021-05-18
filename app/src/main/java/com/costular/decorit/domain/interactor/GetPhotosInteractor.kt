package com.costular.decorit.domain.interactor

import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams
import com.costular.decorit.domain.repository.PhotoRepository
import com.costular.decorit.domain.repository.SettingsRepository

class GetPhotosInteractor(
    private val photoRepository: PhotoRepository
) : ResultInteractor<GetPhotosInteractor.Params, List<Photo>>() {

    data class Params(
        val page: Int,
        val perPage: Int,
        val searchParams: SearchParams
    )

    override suspend fun doWork(params: Params): List<Photo> {
        return photoRepository.getPhotos(
            params.page,
            params.perPage,
            params.searchParams
        )
    }

}