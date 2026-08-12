plugins {
    kotlin("jvm") version "2.3.0"
    java
    application
}

java {
    toolchain {
        languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(23))
    }
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        // set the JVM target to Java 23
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget("23"))
    }
}

application {
    mainClass.set("de.werner.adventofcode.RunKt")
}

tasks {
    wrapper {
        gradleVersion = "9.4.1"
    }
}
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
}