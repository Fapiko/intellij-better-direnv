fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("org.jetbrains.intellij")
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    version.set(properties("platformVersion"))

    // Plugin Dependencies
    plugins.set(listOf("com.intellij.java"))
}

dependencies {
    implementation(project(":better_direnv-core"))
//    implementation("com.google.code.gson:gson:2.9.0")
}
