import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("org.jetbrains.intellij.platform.module")
    id("io.freefair.lombok") version "8.10.2"
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    intellijPlatform {
        intellijIdeaUltimate(providers.gradleProperty("platformVersion"))
        testFramework(TestFrameworkType.Platform)
    }
}
