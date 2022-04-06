package com.d10ng.gps

import android.content.Context
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.d10ng.latlnglib.bean.DLatLng
import com.d10ng.latlnglib.constant.CoordinateSystemType
import com.d10ng.latlnglib.convert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * 高德定位工具
 * @Author: D10NG
 * @Time: 2021/3/3 5:51 下午
 */
class GaodeLocationUtils(context: Context) {

    val locationLive: MutableStateFlow<AMapLocation?> = MutableStateFlow(null)

    private lateinit var locationClient: AMapLocationClient

    companion object {

        @Volatile
        private var INSTANCE: GaodeLocationUtils? = null

        @JvmStatic
        fun instant(context: Context) : GaodeLocationUtils =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: GaodeLocationUtils(context).also {
                    INSTANCE = it
                }
            }

        @JvmStatic
        fun destroy() {
            INSTANCE?.locationClient?.onDestroy()
            INSTANCE = null
        }
    }

    init {
        AMapLocationClient.updatePrivacyShow(context, true, true)
        AMapLocationClient.updatePrivacyAgree(context, true)
        try {
            locationClient = AMapLocationClient(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val mLocationOption = AMapLocationClientOption().apply {
            this.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            interval = 2000
        }
        locationClient.setLocationOption(mLocationOption)
        locationClient.setLocationListener {
            if (it.errorCode != 0) return@setLocationListener
            // 定位数据更新，转换成WGS84
            val map = it.apply {
                val latlng = DLatLng(this.latitude, it.longitude)
                    .convert(CoordinateSystemType.GCJ02, CoordinateSystemType.WGS84)
                this.latitude = latlng.latitude
                this.longitude = latlng.longitude
            }
            locationLive.update { map }
        }
    }

    /**
     * 开启定位
     */
    fun start() {
        if (locationClient.isStarted) {
            locationClient.stopLocation()
        }
        locationClient.startLocation()
    }

    /**
     * 停止定位
     */
    fun stop() {
        locationClient.stopLocation()
    }

    /**
     * 设置定位配置
     * @param option AMapLocationClientOption
     */
    fun setLocationOption(option: AMapLocationClientOption) {
        locationClient.setLocationOption(option)
    }
}