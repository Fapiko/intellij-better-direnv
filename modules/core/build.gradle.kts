fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("org.jetbrains.intellij")
    // Gradle Lombok plugin
    id("io.freefair.lombok") version "6.4.3"
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    version.set(properties("platformVersion"))
}
