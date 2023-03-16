package com.d10ng.gps

import com.d10ng.latlnglib.bean.DLatLng
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * 计算两个点之间的距离
 *
 * @receiver DLatLng
 * @param other DLatLng
 * @return Double 单位米
 */
fun DLatLng.distanceTo(other: DLatLng): Double {
    // 地球半径
    val r = 6378137.0
    val lat1 = this.latitude * Math.PI / 180.0
    val lat2 = other.latitude * Math.PI / 180.0
    val a = lat1 - lat2
    val b = (this.longitude - other.longitude) * Math.PI / 180.0
    val sa2 = sin(a / 2.0)
    val sb2 = sin(b / 2.0)
    return 2 * r * asin(sqrt(sa2 * sa2 + cos(lat1) * cos(lat2) * sb2 * sb2))
}