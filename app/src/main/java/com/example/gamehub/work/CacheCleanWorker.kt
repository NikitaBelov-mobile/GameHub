package com.example.gamehub.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.gamehub.data.local.AppDatabase
import java.util.concurrent.TimeUnit

class CacheCleanWorker(
    context: Context,
    params: WorkerParameters,
    private val db: AppDatabase,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val cutoff = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
        db.gameDao().deleteOlderThan(cutoff)
        return Result.success()
    }

    companion object {
        private const val WORK_NAME = "cache_clean"

        fun schedule(context: Context, db: AppDatabase) {
            val request = PeriodicWorkRequestBuilder<CacheCleanWorker>(1, TimeUnit.DAYS)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.UNMETERED)
                        .build()
                )
                .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}
