plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.mubarak.osmdemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mubarak.osmdemo"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Mapsforge core
    implementation (libs.mapsforge.core)
    implementation (libs.mapsforge.map)
    implementation (libs.mapsforge.mapsforge.map.reader)
    implementation (libs.mapsforge.mapsforge.themes)

    // Mapsforge Android dependency
    implementation (libs.mapsforge.mapsforge.map.android)
    // AndroidSVG by Paul LeBeau
    implementation (libs.androidsvg)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}