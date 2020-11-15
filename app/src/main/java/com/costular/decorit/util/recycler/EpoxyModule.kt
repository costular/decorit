package com.costular.decorit.util.recycler

import com.costular.decorit.util.initializers.AppInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoSet

@InstallIn(ApplicationComponent::class)
@Module
abstract class EpoxyModule {

    @Binds
    @IntoSet
    abstract fun provideEpoxyInitializer(bind: EpoxyInitializer): AppInitializer

}