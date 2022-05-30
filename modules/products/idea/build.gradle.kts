fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("org.jetbrains.intellij")
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    pluginName.set(properties("pluginName"))
    version.set("2021.1.3")
    type.set("IC")

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    plugins.set(listOf("com.intellij.java"))
}

dependencies {
    implementation(project(":better_direnv-core"))
//    implementation("com.google.code.gson:gson:2.9.0")
}
