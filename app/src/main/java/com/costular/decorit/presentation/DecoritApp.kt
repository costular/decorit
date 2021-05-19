package com.costular.decorit.presentation

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkerFactory
import com.costular.decorit.BuildConfig
import com.costular.decorit.util.initializers.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class DecoritApp : Application(), Configuration.Provider {

    // @Inject lateinit var initializers: AppInitializers
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        //initializers.init(this)

        // Debug logger
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()

}