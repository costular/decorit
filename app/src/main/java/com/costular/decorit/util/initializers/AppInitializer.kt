package com.costular.decorit.util.initializers

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}