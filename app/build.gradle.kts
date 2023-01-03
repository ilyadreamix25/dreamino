plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

val composeVersion: String by rootProject.extra

android {
    namespace  = "ua.ilyadreamix.amino"
    compileSdk = 33

    defaultConfig {
        minSdk    = 24
        targetSdk = 33
        versionCode = 5
        versionName = "0.0.04"
        applicationId = "ua.ilyadreamix.amino"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }

    packagingOptions {
        resources {
            excludes += "META-INF/*"
        }
    }

    kapt {
        javacOptions {
            option("-Adagger.fastInit=ENABLED")
            option("-Adagger.hilt.android.internal.disableAndroidSuperclassValidation=true")
        }
    }
}

val composeBomVersion: String by rootProject.extra
val composeActivityVersion: String by rootProject.extra
val composeConstraintLayoutVersion: String by rootProject.extra
val composeNavVersion: String by rootProject.extra
val shimmerVersion: String by rootProject.extra

val coreKtxVersion: String by rootProject.extra
val lifecycleRuntimeKtxVersion: String by rootProject.extra

val coilVersion: String by rootProject.extra
val ktorVersion: String by rootProject.extra
val serializationVersion: String by rootProject.extra

val daggerHiltVersion: String by rootProject.extra

val commonsCodecVersion: String by rootProject.extra
val commonsLangVersion: String by rootProject.extra

dependencies {
    // UI
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation("androidx.activity:activity-compose:$composeActivityVersion")
    implementation("androidx.constraintlayout:constraintlayout-compose:$composeConstraintLayoutVersion")
    implementation("com.valentinilk.shimmer:compose-shimmer:$shimmerVersion")
    implementation("com.google.accompanist:accompanist-flowlayout:0.28.0")

    // Compose BOM dependencies
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.material:material")

    // Coroutines, async, etc.
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeKtxVersion")
    implementation("androidx.navigation:navigation-compose:$composeNavVersion")

    // HTTP
    implementation("io.coil-kt:coil-gif:$coilVersion")
    implementation("io.coil-kt:coil-compose:$coilVersion")
    implementation(platform("io.ktor:ktor-bom:$ktorVersion"))
    implementation("io.ktor:ktor-client-android")
    implementation("io.ktor:ktor-client-serialization-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-client-core-jvm")
    implementation("io.ktor:ktor-client-logging-jvm")
    implementation("io.ktor:ktor-client-json-jvm")
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation("org.slf4j:slf4j-simple:2.0.6")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
    kapt("com.google.dagger:hilt-compiler:$daggerHiltVersion")

    // Hash utilities
    implementation("commons-codec:commons-codec:$commonsCodecVersion")
    implementation("org.apache.commons:commons-lang3:$commonsLangVersion")

    // Previews
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}