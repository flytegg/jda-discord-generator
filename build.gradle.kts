plugins {
    kotlin("jvm") version "1.9.0"
}

group = "gg.flyte"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.flyte.gg/releases")
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.15") {
        exclude(module = "opus-java")
    }
    implementation("gg.flyte:discordgenerator:1.0.4")
}
