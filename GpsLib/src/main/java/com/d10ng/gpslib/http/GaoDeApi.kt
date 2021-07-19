package com.d10ng.gpslib.http

import com.d10ng.gpslib.bean.ReverseGeocodeResult
import com.d10ng.latlnglib.bean.DLatLng
import com.d10ng.latlnglib.constant.CoordinateSystemType
import com.d10ng.latlnglib.convert
import com.d10ng.moshilib.toMoshiBean
import com.d10ng.stringlib.toDString
import com.dlong.dl10retrofitcoroutineslib.DLResponse
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 高德地图 web服务 接口
 *
 * @Author: D10NG
 * @Time: 2021/6/28 4:21 下午
 */
interface GaoDeApi {

    /**
     * 逆地理编码
     * 参考文档：https://lbs.amap.com/api/webservice/guide/api/georegeo
     * @param location String 经度在前，纬度在后，经纬度间以“,”分割，经纬度小数点后不要超过 6 位。eg:99.006916,32.329071
     * @param key String 用户在高德地图官网申请Web服务API类型Key
     * @param extensions String
     * @param batch String
     * @param output String
     * @return DLResponse<ReverseGeocodeResult>
     */
    @GET("https://restapi.amap.com/v3/geocode/regeo")
    suspend fun reverseGeocode(
        @Query("location") location: String,
        @Query("key") key: String,
        @Query("extensions") extensions: String = "base",
        @Query("batch") batch: String = "false",
        @Query("output") output: String = "JSON"
    ): DLResponse<String>
}

/**
 * 逆地理编码
 * @receiver GaoDeApi
 * @param lat Double
 * @param lng Double
 * @param type CoordinateSystemType
 * @param key String
 * @return ReverseGeocodeResult
 */
suspend fun GaoDeApi.getLocationFromLatLng(
    lat: Double,
    lng: Double,
    type: CoordinateSystemType,
    key: String
): ReverseGeocodeResult {
    val dLatLng = if (type == CoordinateSystemType.GCJ02)
        DLatLng(lat, lng)
    else
        DLatLng(lat, lng).convert(type, CoordinateSystemType.GCJ02)
    val result = reverseGeocode(
        "${dLatLng.longitude.toDString(6)},${dLatLng.latitude.toDString(6)}",
        key
    ).body
    return try {
        val json = JSONObject(result)
        json.toMoshiBean(ReverseGeocodeResult::class.java).apply {
            val cityJson = json.optJSONObject("regeocode")?.optJSONObject("addressComponent")?.opt("city")
            if (cityJson is String) {
                this.regeocode?.addressComponent?.city = cityJson
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ReverseGeocodeResult()
    }
}