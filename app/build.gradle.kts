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
    implementation(libs.androidx.activity)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)
    implementation(libs.room.components.ktx)

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
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(libs.test.turbine)
    androidTestImplementation(libs.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.room.testing)

    testImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.junit4)
    androidTestImplementation(libs.androidx.junit.ktx)

}