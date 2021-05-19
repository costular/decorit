package com.costular.decorit.presentation.photos

import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.interactor.GetViewPhotoQualityInteractor
import com.costular.decorit.domain.repository.PhotoRepository
import com.costular.decorit.domain.repository.SettingsRepository
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

    @Provides
    @Singleton
    fun providesGetViewPhotoQualityInteractor(settingsRepository: SettingsRepository): GetViewPhotoQualityInteractor =
        GetViewPhotoQualityInteractor(settingsRepository)

}