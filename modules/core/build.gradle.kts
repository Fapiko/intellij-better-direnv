plugins {
    id("org.jetbrains.intellij")
    // Gradle Lombok plugin
    id("io.freefair.lombok") version "6.4.3"
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    pluginName.set("better_direnv")
    version.set("2021.1.3")
    type.set("IC")

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    plugins.set("".split(',').map(String::trim).filter(String::isNotEmpty))
}
