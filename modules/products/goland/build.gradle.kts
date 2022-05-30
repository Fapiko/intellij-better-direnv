fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("org.jetbrains.intellij")
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    version.set("2021.1.3")
    type.set("GO")

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    plugins.set(listOf("org.jetbrains.plugins.go"))
}

dependencies {
    implementation(project(":better_direnv-core"))
}
