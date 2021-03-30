package com.costular.decorit.di

import com.costular.decorit.presentation.photodetail.PhotoDetailViewModel
import com.costular.decorit.presentation.photos.PhotosViewModel
import com.costular.decorit.presentation.search.SearchViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.IntoMap

@AssistedModule
@Module
@DisableInstallInCheck
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @IntoMap
    @ViewModelKey(PhotosViewModel::class)
    fun photosViewModel(factory: PhotosViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun searchViewModel(factory: SearchViewModel.Factory): AssistedViewModelFactory<*, *>

}