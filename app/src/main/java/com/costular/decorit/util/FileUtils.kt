package com.costular.decorit.util

import android.os.Environment
import com.costular.decorit.BuildConfig
import java.io.File

val LEGACY_ABSOLUTE_PATH = "${
    Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_PICTURES
    )
}${File.separator}Decorit"

const val FILE_PROVIDER_AUTHORITY = "${BuildConfig.APPLICATION_ID}.fileprovider"
