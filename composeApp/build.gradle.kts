import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.2.20"
    id("com.google.gms.google-services")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            // ✅ Keep Android-only stuff here
            implementation(compose.preview)
            implementation("androidx.activity:activity-compose:1.9.3")
            implementation("io.ktor:ktor-client-okhttp:3.3.1")
            implementation(libs.koin.android)
            implementation("io.insert-koin:koin-androidx-compose:4.0.0")
            implementation("com.russhwolf:multiplatform-settings-datastore:1.1.1")
            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:34.6.0"))
            implementation("com.google.firebase:firebase-analytics")
            implementation("com.google.firebase:firebase-messaging:25.0.1")

        }

        commonMain.dependencies {
            // ✅ JetBrains Compose Multiplatform — safe for iOS
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)

            implementation("org.jetbrains.skiko:skiko:0.9.37.3")


            // ✅ Ktor + Serialization (Multiplatform)
            implementation("io.ktor:ktor-client-core:3.3.2")
            implementation("io.ktor:ktor-client-content-negotiation:3.3.1")
            implementation("io.ktor:ktor-client-logging:3.0.0")
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.3.1")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

            // ✅ Koin (Multiplatform-safe)
            api(libs.koin.core)
            implementation(libs.koin.compose)
            // Only if you use it safely (no Android lifecycle)

            // ✅ Image loading for Compose Multiplatform
            implementation("media.kamel:kamel-image:1.0.8")
            implementation("media.kamel:kamel-image-default:1.0.8")

            implementation("media.kamel:kamel-image:1.0.8")

            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.1")

            implementation("com.russhwolf:multiplatform-settings:1.3.0")

            implementation("network.chaintech:cmp-image-pick-n-crop:1.1.2")

        }

        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:3.3.1")
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.vk.kmprecipeapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.vk.kmprecipeapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // ✅ Android-only preview tools
    debugImplementation(compose.uiTooling)
}
