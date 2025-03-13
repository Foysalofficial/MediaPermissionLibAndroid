plugins {
    alias(libs.plugins.android.library)
    id("maven-publish")
}

subprojects {
    repositories {
        mavenCentral()
    }
}

android {
    namespace = "com.mediapermissionlib.byfoysaltechyt"
    compileSdk = 35

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
        sourceCompatibility = JavaVersion.VERSION_17 // Match AGP requirement
        targetCompatibility = JavaVersion.VERSION_17
    }

    publishing {
        singleVariant("release") { // Define the variant to publish
            withSourcesJar() // Optional: Include source code
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation("androidx.activity:activity:1.10.1")
    implementation("androidx.core:core:1.13.1")
}

publishing {
    publications {
        create<MavenPublication>("releaseAar") {
            groupId = "com.github.Foysalofficial"
            artifactId = "mediapermissionlibbyft"
            version = "3.0" // Updated to 3.0 as per your snippet

            // Link to the release component
            from(components["release"])
        }
    }
}