plugins {
    id(Android.Plugin.library)
    id(Kotlin.Plugin.ID.android)
    id(Kotlin.Plugin.ID.kapt)
    id(Kotlin.Plugin.ID.parcelize)
    id(Maven.Plugin.public)
}

group = "com.github.D10NG"

android {
    compileSdk = Project.compile_sdk

    defaultConfig {
        minSdk = Project.min_sdk
        targetSdk = Project.target_sdk

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
        jvmTarget = Kotlin.jvm_target_1_8
    }
}

dependencies {

    // Android
    implementation(Android.X.core_ktx)

    // 单元测试（可选）
    testImplementation(Test.junit)
    androidTestImplementation(Android.Test.junit)
    androidTestImplementation(Android.Test.espresso)

    // kotlin
    implementation(Kotlin.stdlib)

    // Coroutines
    implementation(Kotlin.Coroutines.core)
    implementation(Kotlin.Coroutines.android)

    // 经纬度工具
    api(D10NG.latlngUtil)
    // 高德定位
    implementation("com.amap.api:location:latest.integration")
}

afterEvaluate {
    publishing {
        publications {
            create(Publish.release, MavenPublication::class) {
                from(components.getByName(Publish.release))
            }
        }
    }
}