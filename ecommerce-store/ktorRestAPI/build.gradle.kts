import java.util.regex.Pattern.compile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposedVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.6.0"
}

group = "uj.edu.pl"
version = "0.0.1"
application {
    mainClass.set("uj.edu.pl.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-gson:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    compile("org.xerial:sqlite-jdbc:3.30.1")
}