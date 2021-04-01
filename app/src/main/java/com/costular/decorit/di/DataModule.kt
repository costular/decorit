package com.costular.decorit.di

import android.content.Context
import com.costular.decorit.data.repository.DefaultPhotoRepository
import com.costular.decorit.data.unsplash.UnsplashApi
import com.costular.decorit.data.unsplash.UnsplashPhotoDataSource
import com.costular.decorit.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    fun providesUnsplashApi(@Unsplash retrofit: Retrofit): UnsplashApi = retrofit.create()

    @Provides
    @Singleton
    fun providesUnsplashPhotoDataSource(unsplashApi: UnsplashApi): UnsplashPhotoDataSource =
        UnsplashPhotoDataSource(unsplashApi)

    @Provides
    @Singleton
    fun providesPhotoRepository(unsplash: UnsplashPhotoDataSource): PhotoRepository =
        DefaultPhotoRepository(unsplash)

}