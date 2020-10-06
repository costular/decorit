package com.costular.decorit.presentation

import android.app.Application
import com.costular.decorit.BuildConfig
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DecoritApp : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        AndroidThreeTen.init(this)

        // Debug logger
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}