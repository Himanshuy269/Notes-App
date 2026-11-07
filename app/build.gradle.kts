plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
   // id("com.google.dagger.hilt.android") version "2.57.2" apply false
    //id("com.google.devtools.ksp") version "2.1.10-1.0.29" apply false
//    id("org.jetbrains.kotlin.kapt")
//    id("com.google.dagger.hilt.android")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)

}

android {
    namespace = "com.example.notesapp"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.notesapp"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    //compose
    // Lifecycle ViewModel Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.11.0")
// Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.10.2")
// Material Icons Extended (legacy library)
    implementation("androidx.compose.material:material-icons-extended:1.12.1")
// Hilt Navigation Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.4.0")

    implementation("com.google.dagger:hilt-android:2.60.1")
    ksp("com.google.dagger:hilt-compiler:2.60.1")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.11.0")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.60.1")
    ksp("com.google.dagger:hilt-compiler:2.60.1")

// Hilt Navigation Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.4.0")
    // Room
    implementation("androidx.room:room-runtime:2.8.5")
    implementation("androidx.room:room-ktx:2.8.5")
    ksp("androidx.room:room-compiler:2.8.5")

        // Dagger Hilt
        implementation("com.google.dagger:hilt-android:2.60.1")
        ksp("com.google.dagger:hilt-compiler:2.60.1")
        implementation("androidx.hilt:hilt-navigation-compose:1.4.0")

        // Room Database
        implementation("androidx.room:room-runtime:2.8.5")
        implementation("androidx.room:room-ktx:2.8.5")
        ksp("androidx.room:room-compiler:2.8.5")
    }
