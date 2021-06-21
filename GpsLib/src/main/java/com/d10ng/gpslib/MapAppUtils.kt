package com.d10ng.gpslib

import android.app.Activity
import android.content.Intent
import android.net.Uri

/**
 * 启动百度地图，显示地标位置
 * http://api.map.baidu.com/marker?location=40.047669,116.313082&title=我的位置&content=百度奎科大厦&output=html&src=webapp.baidu.openAPIdemo
 * @param lat Double
 * @param lng Double
 * @param title String
 * @param content String
 * @param coordinate String bd09ll（百度经纬度坐标）bd09mc（百度墨卡托坐标）gcj02（经国测局加密的坐标)wgs84（gps获取的原始坐标）
 */
fun Activity.startBaiDuMapMaker(
    lat: Double,
    lng: Double,
    title: String = "位置",
    content: String = "",
    coordinate: String = "gcj02"
) {
    val builder = StringBuilder("http://api.map.baidu.com/marker?").apply {
        append("location=$lat,$lng")
        append("&title=$title")
        append("&content=$content")
        append("&coord_type=$coordinate")
        append("&output=html&src=webapp.baidu.openAPIdemo")
    }
    goToBrowser(builder.toString())
}

/**
 * 启动百度地图，规划驾车路径
 * http://api.map.baidu.com/direction?origin=latlng:34.264642646862,108.95108518068|name:起点&destination=latlng:23.157471,113.042738|name:终点&mode=driving&region=1&output=html&src=webapp.baidu.openAPIdemo
 * @receiver Activity
 * @param startLat Double
 * @param startLng Double
 * @param startName String
 * @param endLat Double
 * @param endLng Double
 * @param endName String
 * @param coordinate String bd09ll（百度经纬度坐标）bd09mc（百度墨卡托坐标）gcj02（经国测局加密的坐标)wgs84（gps获取的原始坐标）
 */
fun Activity.startBaiduMapNavigation(
    startLat: Double,
    startLng: Double,
    startName: String = "起点",
    endLat: Double,
    endLng: Double,
    endName: String = "终点",
    coordinate: String = "gcj02"
) {
    val builder = StringBuilder("http://api.map.baidu.com/direction?").apply {
        append("origin=latlng:$startLat,$startLng|name:$startName")
        append("&destination=latlng:$endLat,$endLng|name:$endName")
        append("&coord_type=$coordinate")
        append("&mode=driving&region=1&output=html&src=webapp.baidu.openAPIdemo")
    }
    goToBrowser(builder.toString())
}

/**
 * 启动高德地图，显示地标位置
 * https://uri.amap.com/marker?position=121.287689,31.234527&name=park&src=mypage&coordinate=gaode&callnative=0
 * @receiver Activity
 * @param lat Double
 * @param lng Double
 * @param name String
 * @param coordinate String 坐标系参数coordinate=gaode,表示高德坐标（gcj02坐标），coordinate=wgs84,表示wgs84坐标（GPS原始坐标）
 */
fun Activity.startGaoDeMapMaker(
    lat: Double,
    lng: Double,
    name: String = "位置",
    coordinate: String = "gcj02"
) {
    val builder = StringBuilder("https://uri.amap.com/marker?").apply {
        append("position=$lng,$lat")
        append("&name=$name")
        append("&coordinate=$coordinate")
        // 是否尝试调起高德地图APP并在APP中查看，0表示不调起，1表示调起, 默认值为0
        append("&callnative=1")
    }
    goToBrowser(builder.toString())
}

/**
 * 启动高德地图，规划驾车路径
 * https://uri.amap.com/navigation?from=116.478346,39.997361,startpoint&to=116.3246,39.966577,endpoint&via=116.402796,39.936915,midwaypoint&mode=car&policy=1&src=mypage&coordinate=gaode&callnative=0
 * @receiver Activity
 * @param startLat Double
 * @param startLng Double
 * @param startName String
 * @param endLat Double
 * @param endLng Double
 * @param endName String
 * @param coordinate String 坐标系参数coordinate=gaode,表示高德坐标（gcj02坐标），coordinate=wgs84,表示wgs84坐标（GPS原始坐标）
 */
fun Activity.startGaoDeMapNavigation(
    startLat: Double,
    startLng: Double,
    startName: String = "起点",
    endLat: Double,
    endLng: Double,
    endName: String = "终点",
    coordinate: String = "gcj02"
) {
    val builder = StringBuilder("https://uri.amap.com/navigation?").apply {
        append("from=$startLng,$startLat,$startName")
        append("&to=$endLng,$endLat,$endName")
        // 出行方式：驾车：mode=car；公交：mode=bus；步行：mode=walk；骑行：mode=ride；
        append("&mode=car")
        // 当mode=car(驾车)：0:推荐策略,1:避免拥堵,2:避免收费,3:不走高速（仅限移动端）当mode=bus(公交):0:最佳路线,1:换乘少,2:步行少,3:不坐地铁
        append("&policy=0")
        append("&coordinate=$coordinate")
        // 是否尝试调起高德地图APP并在APP中查看，0表示不调起，1表示调起, 默认值为0
        append("&callnative=1")
    }
    goToBrowser(builder.toString())
}

/**
 * 打开浏览器访问网站
 * @param url 网站地址
 */
private fun Activity.goToBrowser(url: String) {
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    startActivity(intent)
}