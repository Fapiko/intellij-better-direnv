plugins {
    id("org.jetbrains.intellij.platform.module")
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
        intellijIdeaUltimate(providers.gradleProperty("platformVersion"))
        plugin("org.jetbrains.plugins.ruby:${providers.gradleProperty("rubyPluginVersion").get()}")
    }
}
