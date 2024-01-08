@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
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

    api(projects.data)

    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.mockk)

    implementation(libs.javax.inject)

    implementation(projects.core)
    implementation(projects.data)
}