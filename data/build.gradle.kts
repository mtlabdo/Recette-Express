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


    // To use Kotlin annotation processing tool (kapt)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)

    implementation(libs.moshi.kotlin)

    // Coroutine test
    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.junit)
}

