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
    implementation 'com.github.D10NGYANG:DLGpsUtil:1.5'
    // 字符串字节数据工具
    implementation 'com.github.D10NGYANG:DLStringUtil:1.7'
    // 经纬度工具
    implementation 'com.github.D10NGYANG:DLLatLngUtil:1.0'
    // 如果需要高德定位
    implementation 'com.amap.api:location:latest.integration'
}
```
3 混淆
```kotlin
-keep class com.d10ng.gpslib.** {*;}
-dontwarn com.d10ng.gpslib.**

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

# Retrofit2
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# retrofit回复处理
-keep class com.dlong.dl10retrofitcoroutineslib.** {*;}
-dontwarn com.dlong.dl10retrofitcoroutineslib.**

# Moshiss
-dontwarn javax.annotation.**
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}
-keep @com.squareup.moshi.JsonQualifier interface *
-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
    <fields>;
    **[] values();
}
-keepclassmembers class com.squareup.moshi.internal.Util {
    private static java.lang.String getKotlinMetadataClassName();
}

# moshi拓展工具
-keep class com.d10ng.moshilib.** {*;}
-dontwarn com.d10ng.moshilib.**

# 经纬度工具
-keep class com.d10ng.latlnglib.** {*;}
-dontwarn com.d10ng.latlnglib.**
```

4 高德接入
[高德定位SDK-入门指南](https://lbs.amap.com/api/android-location-sdk/gettingstarted)
