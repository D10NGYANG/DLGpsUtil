package com.d10ng.gpslib

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object LocationUtils {

    var last: Location? = null
}

/**
 * 定位监听器
 * @Author: D10NG
 * @Time: 2021/3/3 5:41 下午
 */
class ALocationListener : LocationListener {

    val locationLive: MutableStateFlow<Location?> = MutableStateFlow(LocationUtils.last)

    override fun onLocationChanged(location: Location) {
        // 位置改变
        // 得到的是WGS84格式的定位数据
        LocationUtils.last = location
        locationLive.tryEmit(location)
    }
}

/**
 * 请求定位信息
 * @receiver Context
 * @param provider String 定位数据的提供器，默认仅GPS
 * @param minTimeMs Long 最小定位时间（毫秒），默认1000
 * @param minDistanceM Float 最小定位位移（米），默认1
 * @return MutableLiveData<Location?>?
 */
fun Context.startRequestLocation(
    listener: ALocationListener,
    provider: String = getBestLocationProvider()?: LocationManager.GPS_PROVIDER,
    minTimeMs: Long = 1000,
    minDistanceM: Float = 1f
): StateFlow<Location?>? {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
    if (locationManager == null) println("LocationManager = null")
    if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return null
    }
    try {
        val last = locationManager?.getLastKnownLocation(provider)
        if (last != null) {
            listener.locationLive.tryEmit(last)
        }
        locationManager?.requestLocationUpdates(
            provider,
            minTimeMs,
            minDistanceM,
            listener
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return listener.locationLive
}

/**
 * 获取最好的位置提供器
 * @receiver Context
 * @param accuracy Int
 * @param isAltitudeRequired Boolean
 * @param isBearingRequired Boolean
 * @param isCostAllowed Boolean
 * @param powerRequirement Int
 * @return String?
 */
fun Context.getBestLocationProvider(
    accuracy: Int = Criteria.ACCURACY_FINE,
    isAltitudeRequired: Boolean = true,
    isBearingRequired: Boolean = false,
    isCostAllowed: Boolean = false,
    powerRequirement: Int = Criteria.POWER_LOW
): String? {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
    if (locationManager == null) println("LocationManager = null")
    val criteria = Criteria().apply {
        this.accuracy = accuracy
        this.isAltitudeRequired = isAltitudeRequired
        this.isBearingRequired = isBearingRequired
        this.isCostAllowed = isCostAllowed
        this.powerRequirement = powerRequirement
    }
    return locationManager?.getBestProvider(criteria, true)
}

/**
 * 取消定位请求
 * @receiver Context
 */
fun Context.stopRequestLocation(listener: ALocationListener) {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
    if (locationManager == null) println("LocationManager = null")
    try {
        locationManager?.removeUpdates(listener)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}