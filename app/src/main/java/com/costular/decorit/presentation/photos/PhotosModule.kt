package com.costular.decorit.presentation.photos

import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PhotosModule {

    @Provides
    @Singleton
    fun providesPhotosInteractor(photoRepository: PhotoRepository): GetPhotosInteractor =
        GetPhotosInteractor(photoRepository)

}