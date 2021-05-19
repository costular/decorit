package com.costular.decorit.workers

import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.costular.decorit.util.FILE_PROVIDER_AUTHORITY
import com.costular.decorit.util.LEGACY_ABSOLUTE_PATH
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okio.BufferedSink
import okio.buffer
import okio.sink
import java.io.File
import java.util.*
import java.util.concurrent.CancellationException

@HiltWorker
class DownloadWorker @AssistedInject constructor(
    private val okHttpClient: OkHttpClient,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    companion object {

        const val OutputUri = "Uri"
        const val Progress = "Progress"

        private const val KEY_INPUT_URL = "KEY_INPUT_URL"
        private const val KEY_OUTPUT_FILE_NAME = "KEY_OUTPUT_FILE_NAME"

        fun enqueueDownload(
            context: Context,
            url: String,
            fileName: String
        ): UUID {
            val inputData = workDataOf(
                KEY_INPUT_URL to url,
                KEY_OUTPUT_FILE_NAME to fileName
            )
            val request = OneTimeWorkRequestBuilder<DownloadWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .setRequiresStorageNotLow(true)
                        .build()
                )
                .setInputData(inputData).build()
            WorkManager.getInstance(context).enqueue(request)
            return request.id
        }
    }

    override suspend fun doWork(): Result {
        val url = inputData.getString(KEY_INPUT_URL) ?: return Result.failure()
        val fileName = inputData.getString(KEY_OUTPUT_FILE_NAME) ?: return Result.failure()

        val notificationId = id.hashCode()
        val cancelIntent = WorkManager.getInstance(applicationContext).createCancelPendingIntent(id)
        val notificationBuilder = getProgressNotificationBuilder(fileName, cancelIntent)

        val response = download(
            url,
            fileName,
            onProgress = { progress ->
                val progressData = workDataOf(Progress to progress)
                setProgress(progressData)
                // TODO: 5/4/21 show notification as well
            }
        )

        return when (response) {
            is DownloadResponse.Success -> Result.success(workDataOf(OutputUri to response.uri.toString()))
            else -> Result.failure()
        }
    }

    private suspend fun download(
        url: String,
        fileName: String,
        onProgress: suspend (progress: Int) -> Unit
    ): DownloadResponse {
        val request = Request.Builder().url(url).get().build()
        val response = okHttpClient.newCall(request).execute()

        if (!response.isSuccessful) {
            return DownloadResponse.Failure
        }

        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveScopedStorage(fileName, response.body()!!, onProgress)
        } else {
            saveLegacy(fileName, response.body()!!, onProgress)
        }

        return if (uri != null) {
            DownloadResponse.Success(uri)
        } else {
            DownloadResponse.Failure
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private suspend fun saveScopedStorage(
        fileName: String,
        responseBody: ResponseBody,
        onProgress: suspend (progress: Int) -> Unit,
    ): Uri? {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Decorit/")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val imageUri = applicationContext.contentResolver.insert(collection, values)

        val resolver = applicationContext.contentResolver

        if (imageUri != null) {
            val isComplete =
                resolver.openOutputStream(imageUri)?.use { outputStream ->
                    responseBody.writeToSink(outputStream.sink().buffer(), onProgress = onProgress)
                } ?: false

            values.clear()
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(imageUri, values, null, null)

            if (!isComplete) {
                resolver.delete(imageUri, null, null)
                throw CancellationException("Cancelled by user")
            }
        }

        return imageUri
    }

    private suspend fun saveLegacy(
        fileName: String,
        responseBody: ResponseBody,
        onProgress: suspend (progress: Int) -> Unit
    ): Uri? {
        val path = File(LEGACY_ABSOLUTE_PATH)

        if (!path.exists()) {
            if (!path.mkdirs()) return null
        }

        val file = File(path, fileName)

        val complete = responseBody.writeToSink(file.sink().buffer(), onProgress)

        if (!complete && file.exists()) {
            file.delete()
            throw CancellationException("Cancelled by user")
        }

        MediaScannerConnection.scanFile(
            applicationContext,
            arrayOf(file.absolutePath),
            arrayOf("image/jpeg"),
            null
        )

        return FileProvider.getUriForFile(applicationContext, FILE_PROVIDER_AUTHORITY, file)
    }

    private suspend fun ResponseBody.writeToSink(
        sink: BufferedSink,
        onProgress: suspend ((Int) -> Unit)
    ): Boolean {
        val fileSize = contentLength()

        var totalBytesRead = 0L
        var progressToReport = 0

        while (true) {
            if (isStopped) return false
            val readCount = source().read(sink.buffer, 8192L)
            if (readCount == -1L) break
            sink.emit()
            totalBytesRead += readCount
            val progress = (100.0 * totalBytesRead / fileSize)
            if (progress - progressToReport >= 10) {
                progressToReport = progress.toInt()
                onProgress.invoke(progressToReport)
            }
        }

        sink.close()
        return true
    }

    private fun getProgressNotificationBuilder(
        fileName: String,
        cancelIntent: PendingIntent,
        progress: Int = 0
    ) =
        NotificationCompat.Builder(applicationContext, "").apply {
            priority = NotificationCompat.PRIORITY_LOW
            setSmallIcon(android.R.drawable.stat_sys_download)
            setTicker("")
            setOngoing(true)
            setContentTitle(fileName)
            setProgress(100, progress, true)
            addAction(
                0,
                applicationContext.getString(com.costular.decorit.R.string.cancel),
                cancelIntent
            )
        }

}