import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

fun properties(key: String) = providers.gradleProperty(key)

plugins {
    id("java")
    id("org.jetbrains.intellij.platform")
    id("org.jetbrains.changelog") version "2.2.1"
    id("org.sonarqube") version "3.3"
}

group = properties("pluginGroup").get()
version = properties("pluginVersion").get()

allprojects {
    apply(plugin = "java")

    tasks.withType<JavaCompile> {
        sourceCompatibility = properties("javaVersion").get()
        targetCompatibility = properties("javaVersion").get()
    }
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaUltimate(properties("platformVersion"))
        testFramework(TestFrameworkType.Platform)
        pluginComposedModule(project(":better_direnv-core"))
        pluginComposedModule(project(":better_direnv-products-goland"))
        pluginComposedModule(project(":better_direnv-products-idea"))
        pluginComposedModule(project(":better_direnv-products-nodejs"))
        pluginComposedModule(project(":better_direnv-products-shellscript"))
        pluginComposedModule(project(":better_direnv-products-python"))
        pluginComposedModule(project(":better_direnv-products-phpstorm"))
        pluginComposedModule(project(":better_direnv-products-rubymine"))
    }
}

intellijPlatform {
    pluginConfiguration {
        name = properties("pluginName")
        version = properties("pluginVersion")

        description = provider {
            projectDir.resolve("README.md").readText().lines().run {
                val start = "<!-- Plugin description -->"
                val end = "<!-- Plugin description end -->"

                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end))
            }.joinToString("\n").run { markdownToHTML(this) }
        }

        changeNotes = provider {
            changelog.renderItem(
                changelog.getOrNull(properties("pluginVersion").get()) ?: changelog.getLatest(),
                org.jetbrains.changelog.Changelog.OutputType.HTML
            )
        }

        ideaVersion {
            sinceBuild = properties("pluginSinceBuild")
            untilBuild = provider { properties("pluginUntilBuild").orNull?.takeIf { it.isNotEmpty() } }
        }
    }

    signing {
        certificateChain = provider { File("chain.crt").readText(Charsets.UTF_8) }
        privateKey = provider { File("private.pem").readText(Charsets.UTF_8) }
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
        channels = provider {
            listOf(properties("pluginVersion").get().split('-').getOrElse(1) { "default" }.split('.').first())
        }
    }

    pluginVerification {
        ides {
            recommended()
        }
    }
}

changelog {
    version = properties("pluginVersion").get()
    groups.set(emptyList())
}

sonarqube {
    properties {
        property("sonar.projectKey", "Fapiko_intellij-better-direnv")
        property("sonar.organization", "fapiko")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

tasks {
    wrapper {
        gradleVersion = properties("gradleVersion").get()
    }
}
