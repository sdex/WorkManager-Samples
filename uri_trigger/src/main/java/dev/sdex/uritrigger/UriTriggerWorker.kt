package dev.sdex.uritrigger

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.work.Constraints
import androidx.work.ListenableWorker.Result.success
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class UriTriggerWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("UriTriggerWorker", inputData.toString())
        enqueue(applicationContext)
        return success()
    }

    companion object {

        private val CONTENT_URI = Uri.parse("content://" + MediaStore.AUTHORITY + "/")

        fun enqueue(context: Context) {
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(getWorkRequest())
        }

        private fun getWorkRequest(): WorkRequest {
            val builder = Constraints.Builder()
                .addContentUriTrigger(
                    CONTENT_URI, false
                )
                .addContentUriTrigger(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true
                )
                .addContentUriTrigger(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, true
                )
                .setTriggerContentMaxDelay(5, TimeUnit.SECONDS)
                .setTriggerContentUpdateDelay(5, TimeUnit.SECONDS)
            return OneTimeWorkRequestBuilder<UriTriggerWorker>()
                .addTag("uri_trigger_worker")
                .setConstraints(builder.build())
                .build()
        }
    }
}