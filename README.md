# DLGpsUtil
Android原生定位；高德定位；坐标系转换；格式转换；

# 使用
1 Add it in your root build.gradle at the end of repositories:
```kotlin
allprojects {
  repositories {
    ...
    maven("https://raw.githubusercontent.com/D10NGYANG/maven-repo/main/repository")
  }
}
```

2 Add the dependency
```kotlin
dependencies {
    implementation("com.github.D10NGYANG:DLGpsUtil:2.3.1")
    // 经纬度工具
    implementation("com.github.D10NGYANG:DLLatLngUtil-jvm:1.7.0")
    // 可选 - 高德定位
    implementation("com.amap.api:location:latest.integration")
}
```
3 混淆
```kotlin
-keep class com.d10ng.gpslib.** {*;}
-dontwarn com.d10ng.gpslib..**

# 高德
-dontwarn com.amap.**
-keep class com.amap.api.maps.** { *; }
-keep class com.autonavi.** { *; }
-keep class com.amap.api.trace.** { *; }
-keep class com.amap.api.location.** { *; }
-keep class com.amap.api.fence.** { *; }
-keep class com.loc.**{*;}
-keep class com.amap.api.services.** { *; }
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}
-keep class com.alibaba.idst.nls.**{*;}
-keep class com.alibaba.mit.alitts.**{*;}
-keep class com.google.**{*;}
-keep class com.nlspeech.nlscodec.** {*;}
```

4 高德接入
[高德定位SDK-入门指南](https://lbs.amap.com/api/android-location-sdk/gettingstarted)

## 原生定位
```kotlin
/**
 * 检查定位是否可用
 * @receiver Context
 * @return Boolean
 */
fun Context.isLocationEnabled(): Boolean
```
```kotlin
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
): StateFlow<Location?>?
```
```kotlin
/**
 * 取消定位请求
 * @receiver Context
 */
fun Context.stopRequestLocation(listener: ALocationListener)
```
