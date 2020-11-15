package com.costular.decorit.util.initializers

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThreeTenInitializer @Inject constructor() : AppInitializer {

    override fun init(application: Application) {
        AndroidThreeTen.init(application)
    }

}