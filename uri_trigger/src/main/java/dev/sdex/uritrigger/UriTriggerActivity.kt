package dev.sdex.uritrigger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class UriTriggerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uri_trigger)
        UriTriggerWorker.enqueue(this)
    }
}