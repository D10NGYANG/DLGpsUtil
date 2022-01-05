plugins {
    id(Android.plugins_library)
    id(Kotlin.plugins_android)
    id(Kotlin.plugins_kapt)
    id(Kotlin.plugins_parcelize)
    id(Maven.public)
}

group = "com.github.D10NG"

android {
    compileSdk = Android.compile_sdk

    defaultConfig {
        minSdk = Android.min_sdk
        targetSdk = Android.target_sdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Kotlin.jvm_target
    }
}

dependencies {

    // Android
    implementation(Android.androidx_core)

    // 单元测试（可选）
    testImplementation(Test.junit)

    // kotlin
    implementation(Kotlin.stdlib)

    // Lifecycle
    implementation(Lifecycle.runtime)
    implementation(Lifecycle.compiler)
    implementation(Lifecycle.livedata_support)

    // Coroutines
    implementation(Kotlin.coroutines_core)
    implementation(Kotlin.coroutines_android)

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    // retrofit回复处理
    implementation(D10NG.retrofitCoroutines)

    // moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.12.0")
    // moshi拓展工具
    implementation(D10NG.moshiUtil)

    // 字符串字节数据工具
    implementation(D10NG.textUtil)
    // 经纬度工具
    implementation(D10NG.latlngUtil)
    // 高德定位
    implementation("com.amap.api:location:latest.integration")
}

afterEvaluate {
    publishing {
        publications {
            create(Maven.Publish.release, MavenPublication::class) {
                from(components.getByName(Maven.Publish.release))
            }
        }
    }
}