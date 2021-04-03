package com.costular.decorit.presentation.photodetail

import com.costular.decorit.domain.interactor.GetPhotoByIdInteractor
import com.costular.decorit.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PhotoDetailModule {

    @Provides
    @Singleton
    fun providesGetPhotoByIdInteractor(photoRepository: PhotoRepository): GetPhotoByIdInteractor =
        GetPhotoByIdInteractor(photoRepository)

}