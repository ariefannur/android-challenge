import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinxSerialization)
    kotlin("kapt")
}

android {
    namespace = "jp.speakbuddy.core.image"
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
    buildTypes {
        val propFile = File("$rootDir/local.properties")
        val myProperties = Properties()
        myProperties.load(FileInputStream(propFile))
        val token = myProperties.getOrDefault("image.token", "")
        debug {
            buildConfigField("String", "IMAGE_TOKEN", "\"$token\"")
        }
        release {
            buildConfigField("String", "IMAGE_TOKEN", "\"$token\"")
        }
    }

}

// Setup protobuf configuration, generating lite Java and Kotlin classes

dependencies {
    implementation(project(":core:common"))
    kapt(libs.hilt.compiler)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}