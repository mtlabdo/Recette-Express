plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlinAndroid.get().pluginId)
}

android {
    namespace = "com.example.androidtest.repository"
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


    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.mockk)

    implementation(libs.javax.inject)

    implementation(projects.core)
    implementation(projects.data)
}