package com.costular.decorit.presentation

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.costular.decorit.BuildConfig
import com.costular.decorit.util.initializers.AppInitializer
import com.costular.decorit.util.initializers.AppInitializers
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class DecoritApp : Application() {

    @Inject lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initializers.init(this)
        Mavericks.initialize(this)

        // Debug logger
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}