import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    kotlin("jvm") version "1.9.0"
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.GearsNetworkPacket"
version = "1.0-SNAPSHOT"
val mainClassName = "cn.gionrose.facered.Main"

repositories {
    mavenLocal ()
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation ("org.apache.httpcomponents.client5:httpclient5:5.1.3")
    implementation ("org.apache.httpcomponents.client5:httpclient5-fluent:5.1.3")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    implementation("org.apache.httpcomponents.core5:httpcore5:5.2.4")
    implementation("org.slf4j:slf4j-api:2.0.10")
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("1-full")
    dependencies {
        include(dependency("org.apache.httpcomponents.client5:httpclient5:5.1.3"))
        include(dependency("org.apache.httpcomponents.client5:httpclient5-fluent:5.1.3"))
        include(dependency("org.jetbrains.kotlin:kotlin-stdlib:1.9.0"))
        include(dependency("org.apache.httpcomponents.core5:httpcore5:5.2.4"))
        include(dependency("org.slf4j:slf4j-api:2.0.10"))
        include(dependency("com.google.code.gson:gson:2.10.1"))
    }
    manifest {
        attributes(mapOf("Main-Class" to mainClassName))
    }
    mergeServiceFiles()
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set(mainClassName)
}