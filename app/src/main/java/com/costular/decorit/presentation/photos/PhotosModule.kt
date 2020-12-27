package com.costular.decorit.presentation.photos

import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class PhotosModule {

    @Provides
    @Singleton
    fun providesPhotosInteractor(photoRepository: PhotoRepository): GetPhotosInteractor =
        GetPhotosInteractor(photoRepository)

}