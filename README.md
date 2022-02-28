# DLGpsUtil
Android原生定位；高德定位；坐标系转换；格式转换；
[![](https://jitpack.io/v/D10NGYANG/DLGpsUtil.svg)](https://jitpack.io/#D10NGYANG/DLGpsUtil)

# 使用
1 Add it in your root build.gradle at the end of repositories:
```kotlin
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

2 Add the dependency
```kotlin
dependencies {
    implementation 'com.github.D10NGYANG:DLGpsUtil:2.0'
    // 如果需要高德定位
    implementation 'com.amap.api:location:latest.integration'
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
