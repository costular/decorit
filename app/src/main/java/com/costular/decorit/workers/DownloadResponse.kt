package com.costular.decorit.workers

import android.net.Uri

sealed class DownloadResponse {

    data class Success(val uri: Uri) : DownloadResponse()

    object Failure : DownloadResponse()
}