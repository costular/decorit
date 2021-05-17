package com.costular.decorit.presentation.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.costular.decorit.R

class SettingsFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

}