plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
}
android {
    namespace = "jp.speakbuddy.core.common"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
        buildFeatures {
            buildConfig = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    api(libs.androidx.datastore.preferences)
    api(libs.androidx.datastore)
    api(libs.hilt.android)
    api(libs.hilt.compose)
    kapt(libs.hilt.compiler)
    api(libs.protobuf.kotlin)
    api(libs.kotlinx.serialization)
    api(libs.retrofit.kotlinx.serialization)
    api(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

    api(libs.okhttp)
    api(libs.retrofit)
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)

}