// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.9.0" apply false
    id("org.jetbrains.kotlin.android") version "2.1.10" apply false
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.6.21"
    id ("com.google.devtools.ksp") version "2.1.10-1.0.29"
    id ("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.10" apply false
}