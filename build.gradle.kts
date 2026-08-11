
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
    implementation("redis.clients:jedis:7.2.0")
    implementation("com.akuleshov7:ktoml-core:0.5.1")
    implementation("com.akuleshov7:ktoml-file:0.5.1")
    implementation("net.dv8tion:JDA:5.0.0-beta.20")
}

kotlin {
    jvmToolchain(23)
}

tasks.test {
    useJUnitPlatform()
}