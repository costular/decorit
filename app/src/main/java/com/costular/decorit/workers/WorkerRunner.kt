package com.costular.decorit.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkerRunner @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) {

    fun enqueueDownloadWorker(url: String, fileName: String): UUID {
        return DownloadWorker.enqueueDownload(applicationContext, url, fileName)
    }

    suspend fun listenWorkerInfo(uuid: UUID): Flow<WorkInfo> {
        return flow {
            do {
                delay(500) // To avoid spamming
                val value = WorkManager.getInstance(applicationContext)
                    .getWorkInfoById(uuid)
                    .await()
                emit(value)
            } while (!value.state.isFinished)
        }
    }

}