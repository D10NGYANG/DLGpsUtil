package com.d10ng.gpslib.bean

import com.amap.api.location.DPoint
import com.d10ng.latlnglib.bean.DLatLng

/**
 * 格式转换
 * @receiver DPoint
 * @return DLatLng
 */
fun DPoint.toDLatLng(): DLatLng = DLatLng(latitude, longitude)

/**
 * 格式转换
 * @receiver DLatLng
 * @return DPoint
 */
fun DLatLng.toDPoint(): DPoint = DPoint(longitude, latitude)
