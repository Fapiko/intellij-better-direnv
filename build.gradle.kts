import org.jetbrains.changelog.markdownToHTML

fun properties(key: String) = project.findProperty(key).toString()

plugins {
    // Java support
    id("java")
    // Gradle IntelliJ Plugin
    id("org.jetbrains.intellij") version "1.11.0"
    // Gradle Changelog Plugin
    id("org.jetbrains.changelog") version "1.3.1"
    id("org.sonarqube") version "3.3"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

allprojects {
    version = version
    group = group

    apply(plugin = "java")

    repositories {
        mavenCentral()
    }
}


dependencies {
    implementation(project(":better_direnv-products-goland"))
    implementation(project(":better_direnv-products-idea"))
    implementation(project(":better_direnv-products-nodejs"))
    implementation(project(":better_direnv-products-shellscript"))
    implementation(project(":better_direnv-products-python"))
    implementation(project(":better_direnv-products-phpstorm"))
    implementation(project(":better_direnv-products-rubymine"))
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    pluginName.set(properties("pluginName"))
    version.set(properties("platformVersion"))
    type.set("IU")

    updateSinceUntilBuild.set(false)

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    plugins.set("".split(',').map(String::trim).filter(String::isNotEmpty))
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    version.set(properties("pluginVersion"))
    groups.set(emptyList())
}

sonarqube {
    properties {
        property("sonar.projectKey", "Fapiko_intellij-better-direnv")
        property("sonar.organization", "fapiko")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

gradle.taskGraph.whenReady(closureOf<TaskExecutionGraph> {
    val ignoreSubprojectTasks = listOf(
        "buildSearchableOptions", "listProductsReleases", "patchPluginXml", "publishPlugin", "runIde", "runPluginVerifier",
        "verifyPlugin"
    )

    // Don't run some tasks for subprojects
    for (task in allTasks) {
        if (task.project != task.project.rootProject) {
            when (task.name) {
                in ignoreSubprojectTasks -> task.enabled = false
            }
        }
    }
})

tasks {
    // Set the JVM compatibility versions
    properties("javaVersion").let {
        withType<JavaCompile> {
            sourceCompatibility = it
            targetCompatibility = it
        }
    }

    wrapper {
        gradleVersion = properties("gradleVersion")
    }

    patchPluginXml {
        version.set(properties("pluginVersion"))
        sinceBuild.set(properties("pluginSinceBuild"))
        untilBuild.set(properties("pluginUntilBuild"))

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription.set(
            projectDir.resolve("README.md").readText().lines().run {
                val start = "<!-- Plugin description -->"
                val end = "<!-- Plugin description end -->"

                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end))
            }.joinToString("\n").run { markdownToHTML(this) }
        )

        // Get the latest available change notes from the changelog file
        changeNotes.set(provider {
            changelog.run {
                getOrNull(properties("pluginVersion")) ?: getLatest()
            }.toHTML()
        })
    }

    // Configure UI tests plugin
    // Read more: https://github.com/JetBrains/intellij-ui-test-robot
    runIdeForUiTests {
        systemProperty("robot-server.port", "8082")
        systemProperty("ide.mac.message.dialogs.as.sheets", "false")
        systemProperty("jb.privacy.policy.text", "<!--999.999-->")
        systemProperty("jb.consents.confirmation.enabled", "false")
    }

    signPlugin {
        certificateChain.set(File("chain.crt").readText(Charsets.UTF_8))
        privateKey.set(File("private.pem").readText(Charsets.UTF_8))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        dependsOn("patchChangelog")
        token.set(System.getenv("PUBLISH_TOKEN"))
        // pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels.set(listOf(properties("pluginVersion").split('-').getOrElse(1) { "default" }.split('.').first()))
    }
}
