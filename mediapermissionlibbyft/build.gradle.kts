plugins {
    alias(libs.plugins.android.library)
    id("maven-publish") // Added for publishing
}

android {
    namespace = "com.mediapermissionlib.byfoysaltechyt"
    compileSdk = 35 // Good choice for latest SDK as of March 2025

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    publishing {
        singleVariant("release") { // Correct syntax with double quotes
            withSourcesJar() // Optional: Include source files in the artifact
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation("androidx.activity:activity:1.10.1")
    implementation("androidx.core:core:1.15.0")

}
