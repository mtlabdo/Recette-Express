plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    kotlin("kapt")

}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


dependencies {

    api(libs.bundles.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.moshi.kotlin)

    // Coroutine test
    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.junit)
}

