plugins {
    kotlin("jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("maven-publish")
}

group = "gg.flyte"
version = "1.1.0"

repositories {
    mavenCentral()
    maven("https://repo.flyte.gg/releases")
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.15") {
        exclude(module = "opus-java")
    }
    implementation("gg.flyte:discordgenerator:1.0.5")
}

publishing {
    repositories {
        maven {
            name = "flyte-repository"
            url = uri(
                "https://repo.flyte.gg/${
                    if (version.toString().endsWith("-SNAPSHOT")) "snapshots" else "releases"
                }"
            )
            credentials {
                username = System.getenv("MAVEN_NAME")
                password = System.getenv("MAVEN_SECRET")
            }
        }
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = group.toString()
                artifactId = "discordgenerator-jda"
                version = version.toString()

                from(components["java"])
            }
        }
    }
}