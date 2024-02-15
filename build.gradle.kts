import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("maven-publish")
    `maven-publish`
}

val groupName = "io.vexel"
val libVersion = "1.0.0"
val artifactName = "kobold-parsing-kit"

group = groupName
version = libVersion

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    publications {
        create<MavenPublication>("kobold-parsing-kit") { from(components["kotlin"]) }
    }

    repositories {
        mavenLocal()
    }
}
