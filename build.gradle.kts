group = "space.epix"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.3.71"))
    }
}

plugins {
    kotlin("jvm") version "1.3.71"
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    implementation("com.destroystokyo.paper:paper-api:1.15.2-R0.1-SNAPSHOT")
    implementation("io.prometheus:simpleclient:0.8.1")
    implementation("io.prometheus:simpleclient_hotspot:0.8.1")
    implementation("io.prometheus:simpleclient_httpserver:0.8.1")
    implementation(kotlin("stdlib"))
}
