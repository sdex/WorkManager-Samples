package com.sdex.workmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.sdex.workmanager.ForegroundWorker.Companion.Progress
import com.sdex.workmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workManager = WorkManager.getInstance(this)
        val workRequest = OneTimeWorkRequest.from(ForegroundWorker::class.java)
        workManager.enqueue(workRequest)

        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, Observer { workInfo: WorkInfo? ->
                if (workInfo != null) {
                    val progress = workInfo.progress
                    val value = progress.getInt(Progress, 0)
                    binding.progressBar.progress = value
                }
            })
    }
}
