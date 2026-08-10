
plugins {
    kotlin("jvm") version "2.3.21"
}

group = "ir.nayragames"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test"))
    implementation("org.json:json:20240303")
}

kotlin {
    jvmToolchain(23)
}

tasks.test {
    useJUnitPlatform()
}