// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0-Beta2") // Beta
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.0.0-Beta2-1.0.16") // Pre release
        classpath("org.jetbrains.kotlinx:kover-gradle-plugin:0.7.4")
        classpath("com.google.gms:google-services:4.4.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}