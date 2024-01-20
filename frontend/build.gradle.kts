// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.android.library") version "8.1.4" apply false
    id("org.sonarqube") version "4.4.1.3373"
}

buildscript {
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}

sonar {
    properties {
        property("sonar.projectKey", "DMindek_GroupUP")
        property("sonar.organization", "dmindek")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}