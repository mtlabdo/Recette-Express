plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


dependencies {

    implementation(project(":core"))

    api(libs.bundles.retrofit)
    implementation(libs.moshi.kotlin)

    // Coroutine test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Google truth for assertion
    testImplementation("com.google.truth:truth:1.1.3")

    testImplementation(libs.junit)
}

