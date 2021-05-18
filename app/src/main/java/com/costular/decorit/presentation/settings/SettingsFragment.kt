package com.costular.decorit.presentation.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.costular.decorit.R
import com.costular.decorit.data.repository.settings.SettingsRepositoryImpl

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.sharedPreferencesName = SettingsRepositoryImpl.USER_PREFERENCES_NAME
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

}