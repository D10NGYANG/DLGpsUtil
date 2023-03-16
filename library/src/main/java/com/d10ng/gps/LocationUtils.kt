package com.d10ng.gps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

object LocationUtils {

    var last: Location? = null
}

/**
 * 定位监听器
 * @Author: D10NG
 * @Time: 2021/3/3 5:41 下午
 */
class ALocationListener : LocationListener {

    val locationFlow: MutableStateFlow<Location?> = MutableStateFlow(LocationUtils.last)

    override fun onLocationChanged(location: Location) {
        // 位置改变
        // 得到的是WGS84格式的定位数据
        LocationUtils.last = location
        locationFlow.update { location }
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
    val locationManager = getSystemService(LocationManager::class.java)
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
        val last = locationManager.getLastKnownLocation(provider)
            ?: locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (last != null) {
            listener.locationFlow.update { last }
        }
        locationManager.requestLocationUpdates(
            provider,
            minTimeMs,
            minDistanceM,
            listener
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return listener.locationFlow
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
    isBearingRequired: Boolean = true,
    isCostAllowed: Boolean = true,
    powerRequirement: Int = Criteria.NO_REQUIREMENT
): String? {
    val locationManager = getSystemService(LocationManager::class.java)
    val criteria = Criteria().apply {
        this.accuracy = accuracy
        this.isAltitudeRequired = isAltitudeRequired
        this.isBearingRequired = isBearingRequired
        this.isCostAllowed = isCostAllowed
        this.powerRequirement = powerRequirement
    }
    return locationManager.getBestProvider(criteria, true)
}

/**
 * 取消定位请求
 * @receiver Context
 */
fun Context.stopRequestLocation(listener: ALocationListener) {
    val locationManager = getSystemService(LocationManager::class.java)
    try {
        locationManager.removeUpdates(listener)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * 检查定位是否可用
 * @receiver Context
 * @return Boolean
 */
fun Context.isLocationEnabled(): Boolean {
    val locationManager = getSystemService(LocationManager::class.java)
    return LocationManagerCompat.isLocationEnabled(locationManager)
}