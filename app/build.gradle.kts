plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinxSerialization)
}

android {
    namespace = "com.example.vpn"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.vpn"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Core and Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")

// Jetpack Compose
    implementation(libs.androidx.activity.compose)
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation(platform(libs.androidx.compose.bom))
    implementation("androidx.compose.ui:ui:1.7.4")
    implementation("androidx.compose.ui:ui-graphics:1.7.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.4")
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material-icons-extended:1.7.4")

// Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

// Ktor for networking
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.serialization.kotlinx.json)
    implementation(libs.ktor.client.android)

// Navigation and UI Libraries
    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("io.coil-kt:coil-gif:2.4.0")
    implementation("com.google.android.exoplayer:exoplayer:2.19.1")
    implementation("com.google.android.gms:play-services-ads:23.5.0")

// Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

// Koin for dependency injection
    implementation(platform("io.insert-koin:koin-bom:3.5.4"))
    implementation("io.insert-koin:koin-core")
    implementation("io.insert-koin:koin-android")
    implementation("io.insert-koin:koin-compose")
    implementation("io.insert-koin:koin-annotations:1.3.1")

// Splashscreen
    implementation("androidx.core:core-splashscreen:1.0.1")

}