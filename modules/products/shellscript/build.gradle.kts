plugins {
    id("org.jetbrains.intellij")
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    version.set("2021.1.3")

    plugins.set(listOf("com.jetbrains.sh"))
}
