plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlinx-serialization")
    id ("com.google.devtools.ksp")
    id ("com.google.dagger.hilt.android")
    id ("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.apollographql.apollo") version "4.2.0"
}

android {
    namespace = "com.example.tfg"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tfg"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

apollo {
    service("service") {
        packageName.set("com.graphQL")
        introspection {
            endpointUrl.set("https://bookvers.onrender.com/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

val composeMaterial3Version by extra("1.3.1")

dependencies {
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation(platform("androidx.compose:compose-bom:2025.04.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.navigation:navigation-compose:2.8.9")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.3")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation ("com.google.dagger:hilt-android:2.55")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:{latest_version}")
    implementation ("androidx.palette:palette-ktx:1.0.0")
    implementation("com.airbnb.android:lottie-compose:6.6.0")
    ksp("com.google.dagger:hilt-android-compiler:2.55")
    implementation("androidx.compose.material3:material3:$composeMaterial3Version")
    implementation("com.github.skydoves:cloudy:0.2.6")
    implementation("com.apollographql.apollo:apollo-runtime:4.2.0")
    implementation("androidx.security:security-crypto:1.1.0-alpha07")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")
    implementation ("androidx.datastore:datastore:1.1.4")
    implementation("androidx.datastore:datastore-preferences:1.1.4")
    implementation("androidx.datastore:datastore-preferences-rxjava2:1.1.4")
    implementation("androidx.datastore:datastore-preferences-rxjava3:1.1.4")
    implementation("com.google.accompanist:accompanist-permissions:0.31.3-beta")
    testImplementation ("io.mockk:mockk:1.14.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation(kotlin("test"))
}