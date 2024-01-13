plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlinAndroid.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "com.example.androidtest.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 22
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

}


dependencies {

    implementation(projects.core)

    api(libs.bundles.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.kotlinx.coroutines.core)

    // To use Kotlin annotation processing tool (kapt)
    api(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.moshi.kotlin)

    // Coroutine test
    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.junit)
}

