
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
val fuel = "2.3.1"
val mockVersion = "1.13.5"
val jacksonVersion = "2.15.2"
val slf4jVersion = "2.0.7"
val jsonTokenVersion = "0.11.2"
val javalinVersion = "5.6.1"
val koinVersion = "3.1.2"
val exposedVersion = "0.42.0"
val sqliteVersion = "3.42.0.0"
val h2Version = "2.2.220"
val hibernateValidator = "8.0.1.Final"
val javaxValidationVersion = "2.0.1.Final"
val caffeineVersion = "3.1.8"
val dotenvVersion = "6.4.1"

plugins {
    id("com.google.devtools.ksp") version "1.9.0-1.0.11"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("org.sonarqube") version "4.3.1.3277"

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
    implementation(kotlin("stdlib"))
    // web client
    implementation("com.github.kittinunf.fuel:fuel:$fuel")
    implementation("com.github.kittinunf.fuel:fuel-gson:$fuel")
    // logging
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("io.jsonwebtoken:jjwt-api:$jsonTokenVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jsonTokenVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jsonTokenVersion")
    implementation("io.javalin:javalin:$javalinVersion")
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("org.hibernate:hibernate-validator:$hibernateValidator")
    implementation("javax.validation:validation-api:$javaxValidationVersion")
    implementation("com.github.ben-manes.caffeine:caffeine:$caffeineVersion")
    implementation("io.github.cdimascio:dotenv-kotlin:$dotenvVersion")
    // database
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion") // JDBC dependency
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("com.h2database:h2:$h2Version")
    // test
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:$mockVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.findByPath("ktlintCheck")?.dependsOn("ktlintFormat")
tasks.findByPath("compileKotlin")?.dependsOn("ktlintCheck")

application {
    mainClass.set("ApplicationKt")
}
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ApplicationKt"
    }

    archiveFileName.set("${project.name}.jar")
}

sonar {
    properties {
        property("sonar.projectKey", "nobruin_exchange-hexagonal-example")
        property("sonar.organization", "nobruin")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.exclusions", "**/build/**/*")
        property("sonar.coverage.jacoco.xmlReportPaths", "/build/reports/jacoco/test/jacocoTestReport.xml")
    }
}

tasks.withType<ShadowJar> {
    archiveFileName.set("app.jar")
    manifest {
        attributes["Main-Class"] = "ApplicationKt"
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
