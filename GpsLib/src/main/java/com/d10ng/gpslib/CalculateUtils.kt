package com.d10ng.gpslib

import com.amap.api.location.CoordinateConverter
import com.amap.api.location.DPoint
import com.d10ng.gpslib.bean.DLatLng

/**
 * 计算两个点之间的距离
 *
 * @receiver DLatLng
 * @param other DLatLng
 * @return Float 单位米
 */
fun DLatLng.distanceTo(other: DLatLng): Float {
    return CoordinateConverter.calculateLineDistance(
        DPoint(latitude, longitude),
        DPoint(other.latitude, other.longitude)
    ) / 10
}