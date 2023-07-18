import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
val fuel = "2.3.1"
val mockVersion = "1.13.5"
val jacksonVersion = "2.15.2"
plugins {
    kotlin("jvm") version "1.9.0"
    application
    jacoco
}

group = "jaya.tech"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.kittinunf.fuel:fuel:$fuel")
    implementation("com.github.kittinunf.fuel:fuel-gson:$fuel")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:$mockVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}