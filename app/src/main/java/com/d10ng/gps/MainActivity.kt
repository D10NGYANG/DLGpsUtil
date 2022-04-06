package com.d10ng.gps

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        /*CoroutineScope(Dispatchers.IO).launch {
            DLatLng( 37.311899,127.605025).getGaodeLocation(CoordinateSystemType.GCJ02, "ed7db632ad1840dcd12d08c9ac9c61a1") {
                println(it)
            }
        }*/

        val service = Intent(this, LocationService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(service)
        } else {
            startService(service)
        }
    }

    override fun onResume() {
        super.onResume()

        println("isLocationEnabled = ${isLocationEnabled()}")
    }
}