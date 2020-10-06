package com.costular.decorit.data.local

import android.content.Context
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.createDataStore("settings")



}