package com.costular.decorit.presentation.settings

import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.costular.decorit.R
import com.google.android.material.appbar.MaterialToolbar

class SettingsActivity : AppCompatActivity(R.layout.activity_settings) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<MaterialToolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, SettingsFragment())
            .commit()

    }

}