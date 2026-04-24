plugins {
    id("java")
    id("maven-publish")
    kotlin("jvm")
}

group = "com.nastena.pawsitive"
version = "1.0.0"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.nastena.pawsitive"
            artifactId = "common"
            version = "1.0.0"

            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(24)
}