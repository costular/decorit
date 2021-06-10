package com.costular.decorit.util.initializers

import android.app.Application
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInitializers @Inject constructor(
    private val themeInitializer: ThemeInitializer
) {
    fun init(application: Application) {
        themeInitializer.init(application)
    }
}