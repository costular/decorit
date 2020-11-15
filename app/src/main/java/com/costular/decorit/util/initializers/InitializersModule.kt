package com.costular.decorit.util.initializers

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoSet

@InstallIn(ApplicationComponent::class)
@Module
abstract class InitializersModule {

    @Binds
    @IntoSet
    abstract fun bindsThreeTenInitializer(bind: ThreeTenInitializer): AppInitializer

}