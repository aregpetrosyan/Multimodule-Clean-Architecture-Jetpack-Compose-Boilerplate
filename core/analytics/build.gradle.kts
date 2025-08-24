import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

val localPropsFile = rootProject.file("local.properties")
val localProperties = Properties()
if (localPropsFile.exists()) {
    localProperties.load(localPropsFile.inputStream())
}
val mixpanelToken: String = localProperties.getProperty("MIXPANEL_TOKEN")

android {
    namespace = "com.aregyan.core.analytics"
    compileSdk = 36

    defaultConfig {
        minSdk = 28

        buildConfigField("String", "MIXPANEL_TOKEN", "\"$mixpanelToken\"")
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.mixpanel)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}