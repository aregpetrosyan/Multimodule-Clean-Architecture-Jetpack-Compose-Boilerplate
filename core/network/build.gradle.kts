plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.aregyan.core.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 28
        buildConfigField("String", "UNSPLASH_API_KEY", "\"cXl2S05OM1ExREpCNzhFbnBvZ195V3JUUmhSMll1SnBPNEc5XzFVZWcwcw==\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures.buildConfig = true
}

dependencies {
    implementation(projects.core.data)

    implementation(libs.androidx.core.ktx)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.okhttp.logging)
}