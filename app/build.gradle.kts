plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.devtools.ksp) // https://developer.android.com/build/migrate-to-ksp
}

android {
    namespace = "com.example.exercise_room"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.exercise_room"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures { viewBinding = true }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.ktx)

    // Room components
    implementation(libs.room.components.ktx)
    implementation(libs.androidx.activity)

    ksp(libs.room.compiler)

    testImplementation(libs.room.testing)
    annotationProcessor(libs.room.compiler)

    // test Flow
    androidTestImplementation(libs.test.turbine)

    // Lifecycle components
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // coroutine
    implementation(libs.jetbrains.kotlinx.coroutines.android)

    // UI
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.arch)

//    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.jetbrains.kotlinx.coroutines.test)

    // AndroidX Test - JVM testing、Instrumented testing
    testImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.junit4)
    androidTestImplementation(libs.androidx.junit.ktx)

}