package com.sdex.workmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.sdex.workmanager.ForegroundWorker.Companion.ARG_PROGRESS
import com.sdex.workmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workManager = WorkManager.getInstance(this)

        binding.start.setOnClickListener {
            val workRequest = OneTimeWorkRequest.from(ForegroundWorker::class.java)
            // observe progress
            workManager.getWorkInfoByIdLiveData(workRequest.id)
                .observe(this) { workInfo: WorkInfo? ->
                    if (workInfo != null) {
                        val progress = workInfo.progress
                        val value = progress.getInt(ARG_PROGRESS, 0)
                        binding.progressBar.progress = value

                        if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                            binding.start.isEnabled = true
                        }
                    }
                }
            // run the worker
            workManager.enqueue(workRequest)

            binding.start.isEnabled = false
        }
    }
}
