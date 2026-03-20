fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("org.jetbrains.intellij.platform") version "2.11.0"
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    implementation(project(":better_direnv-core"))
    intellijPlatform {
        create("IU", properties("platformVersion"))
        bundledPlugins(listOf("JavaScript", "NodeJS"))
    }
}
