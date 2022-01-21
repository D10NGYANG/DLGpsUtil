package com.d10ng.dlgpsutil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.d10ng.gpslib.http.getGaodeLocation
import com.d10ng.latlnglib.bean.DLatLng
import com.d10ng.latlnglib.constant.CoordinateSystemType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        CoroutineScope(Dispatchers.IO).launch {
            DLatLng( 37.311899,127.605025).getGaodeLocation(CoordinateSystemType.GCJ02, "ed7db632ad1840dcd12d08c9ac9c61a1") {
                println(it)
            }
        }
    }
}