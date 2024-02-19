import org.jetbrains.kotlin.gradle.plugin.KotlinTargetHierarchy.SourceSetTree.Companion.test

plugins {
    kotlin("jvm") version "1.9.0"
    id("maven-publish")
    `maven-publish`
}

val groupName = "io.vexel"
val libVersion = "1.0.0-SNAPSHOT"
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

tasks.named<Test>("test") {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

publishing {
    publications {
        create<MavenPublication>("kobold-parsing-kit") { from(components["kotlin"]) }
    }

    repositories {
        mavenLocal()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/RowDaBoat/kobold-parsing-kit")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
