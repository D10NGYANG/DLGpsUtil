package com.d10ng.gps.http

import com.d10ng.gps.bean.Regeocode
import com.d10ng.latlnglib.bean.DLatLng
import com.d10ng.latlnglib.constant.CoordinateSystemType
import com.d10ng.latlnglib.convert
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun DLatLng.getGaodeLocation(
    type: CoordinateSystemType,
    key: String,
    onError: (String) -> Unit
): Regeocode? {
    val dLatLng = this.convert(type, CoordinateSystemType.GCJ02)
    val loc = "${dLatLng.longitude},${dLatLng.latitude}"
    val path = StringBuilder("https://restapi.amap.com/v3/geocode/regeo?")
        .append("location=$loc")
        .append("&key=$key")
        .append("&extensions=base&batch=false&output=JSON")

    return suspendCoroutine { cont ->
        val url = URL(path.toString())
        val conn = url.openConnection() as HttpURLConnection
        conn.connectTimeout = 5000
        conn.requestMethod = "GET"
        if (conn.responseCode == 200) {
            val ins = conn.inputStream
            val data = ins.readBytes()
            ins.close()
            val jsonStr = data.toString(Charsets.UTF_8)
            println(jsonStr)
            try {
                val json = JSONObject(jsonStr)
                val status = json.getString("status")
                if (status == "1") {
                    // 请求成功
                    val reg = Regeocode()
                    val regeocodeObj = json.getJSONObject("regeocode")
                    reg.formattedAddress = regeocodeObj.getString("formatted_address").replace("[]", "")
                    val addressComponentObj = regeocodeObj.getJSONObject("addressComponent")
                    reg.country = addressComponentObj.getString("country").replace("[]", "")
                    reg.province = addressComponentObj.getString("province").replace("[]", "")
                    reg.city = addressComponentObj.getString("city").replace("[]", reg.province)
                    reg.citycode = addressComponentObj.getString("citycode").replace("[]", "")
                    reg.district = addressComponentObj.getString("district").replace("[]", "")
                    reg.adcode = addressComponentObj.getString("adcode").replace("[]", "")
                    reg.township = addressComponentObj.getString("township").replace("[]", "")
                    reg.towncode = addressComponentObj.getString("towncode").replace("[]", "")
                    reg.neighborhood = (addressComponentObj.optJSONObject("neighborhood")?: JSONObject())
                        .optString("name").replace("[]", "")
                    reg.building = (addressComponentObj.optJSONObject("building")?: JSONObject())
                        .optString("name").replace("[]", "")
                    println(reg)
                    cont.resume(reg)
                } else {
                    // 请求失败
                    val info = json.getString("info")
                    val infocode = json.getString("infocode")
                    onError.invoke("错误码：$infocode, $info")
                    cont.resume(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onError.invoke("数据解析失败！")
                cont.resume(null)
            }
        } else {
            onError.invoke("错误码：${conn.responseCode}, ${conn.responseMessage}")
            cont.resume(null)
        }
    }
}