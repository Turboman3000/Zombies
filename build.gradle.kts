plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "de.turboman"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("net.minestom:minestom-snapshots:b80c799750")
    implementation("ch.qos.logback:logback-core:1.5.8")
    implementation("ch.qos.logback:logback-classic:1.5.8")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.4.1")
    implementation("dev.hollowcube:polar:1.12.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    if (project.hasProperty("jarfile"))
        archiveFileName.set(project.properties["jarfile"].toString())

    manifest {
        attributes["Main-Class"] = "de.turboman.zombies.Zombies"
    }
}