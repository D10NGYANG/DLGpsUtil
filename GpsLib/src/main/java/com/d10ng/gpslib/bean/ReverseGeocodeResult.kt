package com.d10ng.gpslib.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

/**
 * 高德 逆地理转换回复
 * @Author: D10NG
 * @Time: 2021/1/27 3:22 下午
 */
@JsonClass(generateAdapter = true)
data class ReverseGeocodeResult(
    // 返回值为 0 或 1，0 表示请求失败；1 表示请求成功。
    @Json(name = "status")
    var status: Int = 0,

    // 当 status 为 0 时，info 会返回具体错误原因，否则返回“OK”。
    @Json(name = "info")
    var info: String = "",

    @Json(name = "regeocode")
    var regeocode: Regeocode? = Regeocode()
): Serializable {
    @JsonClass(generateAdapter = true)
    data class Regeocode(
        // 结构化地址信息包括：省份＋城市＋区县＋城镇＋乡村＋街道＋门牌号码
        // 如果坐标点处于海域范围内，则结构化地址信息为：省份＋城市＋区县＋海域信息
        @Json(name = "formatted_address")
        var formattedAddress: String = "",

        // 地址元素列表
        @Json(name = "addressComponent")
        var addressComponent: AddressComponent? = AddressComponent()
    ): Serializable {
        @JsonClass(generateAdapter = true)
        data class AddressComponent(
            // 国家
            @Json(name = "country")
            var country: String = "",

            // 坐标点所在省名称
            @Json(name = "province")
            var province: String = "",

            // 坐标点所在城市名称
            @Transient
            var city: String = "",

            // 城市编码
            @Json(name = "citycode")
            var citycode: String = "",

            // 坐标点所在区
            @Json(name = "district")
            var district: String = "",

            // 行政区编码
            @Json(name = "adcode")
            var adcode: String = "",

            // 坐标点所在乡镇/街道（此街道为社区街道，不是道路信息）
            @Json(name = "township")
            var township: String = "",

            // 乡镇街道编码
            @Json(name = "towncode")
            var towncode: String = "",
        ): Serializable
    }
}