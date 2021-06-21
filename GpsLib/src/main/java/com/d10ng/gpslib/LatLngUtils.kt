package com.d10ng.gpslib

import com.d10ng.gpslib.bean.DLatLng
import com.d10ng.gpslib.constant.CoordinateSystemType
import kotlin.math.abs

/**
 * 经纬度的坐标系转换
 *
 * @receiver [DLatLng]
 * @param from [CoordinateSystemType] 输入坐标系
 * @param to [CoordinateSystemType] 输出坐标系
 * @return [DLatLng]
 */
fun DLatLng.convert(from: CoordinateSystemType, to: CoordinateSystemType): DLatLng {
    when(from) {
        CoordinateSystemType.WGS84 -> {
            when(to) {
                CoordinateSystemType.GCJ02 -> {
                    val dd = CoordinateTransform.transformWGS84ToGCJ02(longitude, latitude)
                    return DLatLng(dd[1],dd[0])
                }
                CoordinateSystemType.BD09 -> {
                    val dd = CoordinateTransform.transformWGS84ToBD09(longitude, latitude)
                    return DLatLng(dd[1],dd[0])
                }
            }
        }
        CoordinateSystemType.GCJ02 -> {
            when(to) {
                CoordinateSystemType.WGS84 -> {
                    val dd = CoordinateTransform.transformGCJ02ToWGS84(longitude, latitude)
                    return DLatLng(dd[1],dd[0])
                }
                CoordinateSystemType.BD09 -> {
                    val dd = CoordinateTransform.transformGCJ02ToBD09(longitude, latitude)
                    return DLatLng(dd[1],dd[0])
                }
            }
        }
        CoordinateSystemType.BD09 -> {
            when(to) {
                CoordinateSystemType.WGS84 -> {
                    val dd = CoordinateTransform.transformBD09ToWGS84(longitude, latitude)
                    return DLatLng(dd[1],dd[0])
                }
                CoordinateSystemType.GCJ02 -> {
                    val dd = CoordinateTransform.transformBD09ToGCJ02(longitude, latitude)
                    return DLatLng(dd[1],dd[0])
                }
            }
        }
    }
    return this
}

/**
 * 将经纬度转换为度分秒格式
 * @receiver [Double] 116.418847
 * @return [String] 116°25'7.85"
 */
fun Double.longlatitude2dfm(): String {
    val du = this.toInt()
    val tp = (this - du) * 60
    val fen = tp.toInt()
    val miao = String.format("%.2f", abs((tp - fen) * 60))
    return du.toString() + "°" + abs(fen) + "'" + miao + "\""
}

/**
 * 度分秒转经纬度
 * @receiver String 116°25'7.85"
 * @return Double 116.418847
 */
fun String.dfm2longlatitude(): Double {
    if (this.isEmpty()) return 0.0
    try {
        val dms = this.replace(" ", "").trim()
        val str2 = dms.split("°").toTypedArray()
        if (str2.size < 2) return 0.0
        val d = str2[0].toInt()
        val str3 = str2[1].split("\'").toTypedArray()
        if (str3.size < 2) return 0.0
        val f = str3[0].toInt()
        val str4 = str3[1].substring(0, str3[1].length - 1)
        val m = str4.toDouble()
        val fen = f + m / 60
        var du = fen / 60 + abs(d)
        if (d < 0) du = -du
        return du
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return 0.0
}

/**
 * 判断经度数据是否为东经
 * @receiver Double
 * @return Boolean
 */
fun Double.isEastLongitude(): Boolean {
    return this in 0.0..180.0
}

/**
 * 判断纬度数据是否为北纬
 * @receiver Double
 * @return Boolean
 */
fun Double.isNorthLatitude(): Boolean {
    return this in 0.0 .. 90.0
}

/**
 * 将经度转换成不带前缀的数据
 * - 当数据大于180度或者小于0度表示西经
 * @receiver Double
 * @return Double 返回去除"-"或小于180的数值；eg：输入181.1，返回1.1; 输入-1.1返回1.1;
 */
fun Double.toLongitudeNoPre(): Double {
    // 收到经度数据"113.0312511"，如果这个数小于等于180代表东经，大于180度表示西经
    var longitudeData = this
    if (longitudeData > 180) {
        longitudeData = 360 - longitudeData
    } else if (longitudeData < 0) {
        longitudeData = 0 - longitudeData
    }
    return longitudeData
}

/**
 * 将纬度转换成不带前缀的数据
 * - 当数据大于90度或者小于0度表示南纬
 * @receiver Double
 * @return Double 返回去除"-"或小于90的数值；eg：输入91.1，返回1.1; 输入-1.1返回1.1;
 */
fun Double.toLatitudeNoPre(): Double {
    // 收到纬度数据"23.1531888",如果这个数小于等于90代表北纬，大于90代表南纬
    var latitudeData = this
    if (latitudeData > 90) {
        latitudeData = 180 - latitudeData
    } else if (latitudeData < 0) {
        latitudeData = 0 - latitudeData
    }
    return latitudeData
}

/**
 * 将经度添加东经标记转换成完整数据
 * - eg: 输入经度=110.1，isEast=false，isPositive=false，输出经度=-110.1
 * - eg: 输入经度=110.1，isEast=false，isPositive=true，输出经度=249.9
 * @receiver Double
 * @param isEast Boolean 是否为东经
 * @param isPositive Boolean 输出数据是否需要为正值
 * @return Double 输出经度
 */
fun Double.toFullLongitude(isEast: Boolean, isPositive: Boolean = true): Double {
   if (!this.isEastLongitude()) {
       // 当前输入的数据，是一个西经完整数据
       if (isPositive) {
           // 输出要求正值
           return if (this > 0) {
               // 输入数据是正值
               this
           } else {
               // 输入数据是负值
               360 + this
           }
       } else {
           // 输出要求负值
           return if (this > 0) {
               // 输入数据是正值
               this - 360
           } else {
               // 输入数据是负值
               this
           }
       }
   } else if (isEast) {
       // 当前输入的数据，是一个东经完整数据
       return this
   } else {
       // 当前输入的数据，是一个西经非完整数据
       return if (isPositive) {
           // 输出要求正值
           360 - this
       } else {
           // 输出要求负值
           - this
       }
   }
}

/**
 * 将纬度添加北纬标记转换成完整数据
 * - eg: 输入纬度=10.1，isNorth=false，isPositive=false，输出纬度=-10.1
 * - eg: 输入纬度=10.1，isNorth=false，isPositive=true，输出纬度=79.9
 * @receiver Double
 * @param isNorth Boolean 是否为北纬
 * @param isPositive Boolean 输出数据是否需要为正值
 * @return Double 输出纬度
 */
fun Double.toFullLatitude(isNorth: Boolean, isPositive: Boolean = true): Double {
    if (!this.isNorthLatitude()) {
        // 当前输入的数据，是一个南纬完整数据
        if (isPositive) {
            // 输出要求正值
            return if (this > 0) {
                // 输入数据是正值
                this
            } else {
                // 输入数据是负值
                90 + this
            }
        } else {
            // 输出要求负值
            return if (this > 0) {
                // 输入数据是正值
                this - 90
            } else {
                // 输入数据是负值
                this
            }
        }
    } else if (isNorth) {
        // 当前输入的数据，是一个北纬完整数据
        return this
    } else {
        // 当前输入的数据，是一个南纬非完整数据
        return if (isPositive) {
            // 输出要求正值
            90 - this
        } else {
            // 输出要求负值
            - this
        }
    }
}

/**
 * 将经度转换成显示数据
 * @receiver Double
 * @return String
 */
fun Double.getShowLongitude(): String {
    val start = if (this.isEastLongitude()) "E" else "W"
    return "$start${this.toLongitudeNoPre().longlatitude2dfm()}"
}

/**
 * 将纬度转换成显示数据
 * @receiver Double
 * @return String
 */
fun Double.getShowLatitude(): String {
    val start = if (this.isNorthLatitude()) "N" else "S"
    return "$start${this.toLatitudeNoPre().longlatitude2dfm()}"
}